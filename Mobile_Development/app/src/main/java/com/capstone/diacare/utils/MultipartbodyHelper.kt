package com.capstone.diacare.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


//
fun uriToMultipartBodyPart(context: Context, uri: Uri, partName: String): MultipartBody.Part? {
    val file = when (uri.scheme) {
        "content" -> File(getPathFromUri(context, uri) ?: return null)
        "file" -> File(uri.path ?: return null)
        else -> return null
    }

    Log.d("MultipartBodyHelper", "uriToMultipartBodyPart file: ${file.name}")

    // Create RequestBody for the file
    val requestFile = file.asRequestBody(context.contentResolver.getType(uri)?.toMediaTypeOrNull())
    Log.d("MultipartBodyHelper", "uriToMultipartBodyPart requestFile: $requestFile")

    // Wrap it in MultipartBody.Part
    val mp = MultipartBody.Part.createFormData(partName, file.name, requestFile)
    Log.d("MultipartBodyHelper", "uriToMultipartBodyPart mp: ${mp.body}")

    return mp
}

// Helper function to get the file path from Uri
fun getPathFromUri(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null) ?: return null
    return try {
        val columnIndex = cursor.getColumnIndexOrThrow("_data")
        cursor.moveToFirst()
        val stringResult = cursor.getString(columnIndex)
        Log.d("MultipartBodyHelper", "result getPathFromUri: $stringResult")
        stringResult
    } catch (e: Exception) {
        Log.d("MultipartBodyHelper", "catch getPathFromUri: ${e.message}")
        null
    } finally {
        cursor.close()
    }
}

/**
 * Converts a Uri to a RequestBody for image/jpeg type.
 *
 * @param context The context to access the content resolver.
 * @param uri The Uri of the file to be converted.
 * @return A RequestBody if successful, null otherwise.
 */
fun uriToRequestBody(context: Context, uri: Uri): RequestBody? {
    return try {
        // Create a temporary file from the Uri
        val file = createTempFileFromUri(context, uri, "file.jpg")
        file?.asRequestBody("image/jpeg".toMediaTypeOrNull())

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * Creates a temporary file from the given Uri.
 *
 * @param context The context to access the content resolver.
 * @param uri The Uri to be converted to a file.
 * @param fileName The name of the temporary file.
 * @return The created temporary File.
 */
private fun createTempFileFromUri(context: Context, uri: Uri, fileName: String): File? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val tempFile = File(context.cacheDir, fileName)
        val outputStream = FileOutputStream(tempFile)

        // Copy the content from inputStream to the temporary file
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}