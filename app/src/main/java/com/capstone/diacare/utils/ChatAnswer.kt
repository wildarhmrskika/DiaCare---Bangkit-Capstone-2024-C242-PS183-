package com.capstone.diacare.utils

fun handleDiabetesQuery(userInput: String): String {
    // Normalize the input to lowercase for easier comparison
    val input = userInput.lowercase()

    return when {
        // Greetings
        input.contains("halo") || input.contains("hi") || input.contains("hai") ||
                input.contains("selamat pagi") || input.contains("selamat siang") ||
                input.contains("selamat sore") || input.contains("selamat malam") ||
                input.contains("halo teman") || input.contains("halo dok") -> {
            "Halo! Senang bisa berbicara dengan Anda. Ada yang ingin Anda tanyakan seputar diabetes? Saya di sini untuk membantu."
        }

        // Causes of diabetes
        input.contains("penyebab diabetes") || input.contains("apa yang menyebabkan diabetes") ||
                input.contains("mengapa orang bisa terkena diabetes") || input.contains("alasan diabetes terjadi") ||
                input.contains("penyebab gula darah tinggi") || input.contains("kenapa bisa kena diabetes") -> {
            "Penyebab diabetes bisa bervariasi, ya. Biasanya, ini karena kombinasi faktor genetik, pola makan, kurang aktivitas fisik, dan kadang stres. Misalnya, pola makan tinggi gula dan obesitas bisa memicu resistensi insulin. Apakah Anda ingin tahu lebih dalam tentang salah satu faktor ini?"
        }

        // Symptoms of diabetes
        input.contains("gejala diabetes") || input.contains("tanda-tanda diabetes") ||
                input.contains("bagaimana mengetahui diabetes") || input.contains("ciri-ciri diabetes") ||
                input.contains("ciri ciri") || input.contains("tanda diabetes") || input.contains("gejala gula darah tinggi") -> {
            "Beberapa gejala umum diabetes antara lain sering merasa haus, sering buang air kecil, cepat lelah, dan penurunan berat badan tanpa sebab yang jelas. Kalau Anda mengalami gejala seperti ini, ada baiknya berkonsultasi dengan dokter. Saya bisa bantu menjelaskan lebih lanjut jika diperlukan."
        }

        // Types of diabetes
        input.contains("jenis diabetes") || input.contains("tipe diabetes") ||
                input.contains("macam-macam diabetes") || input.contains("kategori diabetes") ||
                input.contains("macam macam") || input.contains("jenis penyakit diabetes") -> {
            "Tentu, ada beberapa jenis diabetes: \n" +
                    "1. Diabetes Tipe 1: Biasanya muncul di usia muda karena tubuh tidak memproduksi insulin.\n" +
                    "2. Diabetes Tipe 2: Umum pada orang dewasa, sering terkait gaya hidup.\n" +
                    "3. Diabetes Gestasional: Terjadi selama kehamilan.\n" +
                    "Jika Anda tertarik, saya bisa jelaskan lebih detail tentang salah satu jenis ini."
        }

        // Diabetes prevention
        input.contains("mencegah diabetes") || input.contains("pencegahan diabetes") ||
                input.contains("cara menghindari diabetes") || input.contains("bagaimana mengurangi risiko diabetes") ||
                input.contains("tips cegah diabetes") || input.contains("cara tidak kena diabetes") -> {
            "Mencegah diabetes bisa dimulai dari hal-hal sederhana, seperti menjaga berat badan ideal, makan sehat, dan olahraga rutin. Kalau ada faktor risiko, misalnya riwayat keluarga, penting juga untuk memeriksa kadar gula darah secara rutin. Apa Anda ingin tips lebih spesifik?"
        }

        // Diabetes treatment
        input.contains("mengobati diabetes") || input.contains("pengobatan diabetes") ||
                input.contains("cara merawat diabetes") || input.contains("bagaimana menangani diabetes") ||
                input.contains("penanganan diabetes") || input.contains("cara sembuhkan diabetes") -> {
            "Mengelola diabetes itu kombinasi antara pola makan, olahraga, dan kadang pengobatan seperti insulin atau obat oral. Pemantauan kadar gula darah juga penting agar tetap stabil. Kalau butuh penjelasan soal pengobatan tertentu, beri tahu saya."
        }

        // Long-term complications of diabetes
        input.contains("komplikasi diabetes") || input.contains("efek samping diabetes") ||
                input.contains("dampak jangka panjang diabetes") || input.contains("penyakit akibat diabetes") ||
                input.contains("risiko diabetes") || input.contains("efek diabetes") -> {
            "Diabetes yang tidak terkontrol bisa menyebabkan komplikasi serius, seperti gangguan jantung, kerusakan ginjal, atau gangguan penglihatan. Tapi dengan pengelolaan yang baik, banyak komplikasi ini bisa dicegah. Kalau Anda butuh tips mencegahnya, saya siap bantu."
        }

        // General information about diabetes
        input.contains("apa itu diabetes") || input.contains("diabetes adalah") ||
                input.contains("penjelasan tentang diabetes") || input.contains("informasi diabetes") ||
                input.contains("pengertian diabetes") || input.contains("arti diabetes") -> {
            "Diabetes itu kondisi di mana kadar gula darah terlalu tinggi karena masalah dengan insulin. Insulin ini hormon yang membantu tubuh mengelola gula darah. Ada yang ingin Anda tanyakan lebih spesifik soal ini?"
        }

        // Default response for unknown queries
        else -> {
            "Maaf, saya belum bisa memahami pertanyaan Anda sepenuhnya. Coba beri saya lebih banyak detail atau tanyakan topik lain seputar diabetes. Saya akan berusaha membantu!"
        }
    }
}
