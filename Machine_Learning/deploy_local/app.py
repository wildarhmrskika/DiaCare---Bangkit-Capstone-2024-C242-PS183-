from fastapi import FastAPI, File, UploadFile
from fastapi.responses import HTMLResponse, JSONResponse
from tensorflow.keras.models import load_model
from PIL import Image
import numpy as np
from ultralytics import YOLO

# Initialize the FastAPI
app = FastAPI()

# Load the YOLO and CNN models
yolo_model = YOLO("yolov8_trained.pt")  
cnn_model = load_model("cnn_model.keras")  

# Detect if the image contains nails using YOLO
def detect_nail(file):
    try:
        img = Image.open(file).convert("RGB") #convert to RGB
        results = yolo_model(img)

        # Debugging the results from YOLO
        print("YOLO Results:", results)
        for result in results:
            for label in result.boxes.cls:
                print("Detected Label:", label)
                if label in [0, 1]:  # Ensure 0 and 1 represent nail labels
                    return True
        return False
    except Exception as e:
        print(f"Error in detect_nail: {e}")
        return False

# Preprocess the image
def preprocess_image(file):
    img = Image.open(file).convert("RGB")
    img = img.resize((300, 300))  # Resize input size
    img_array = np.array(img)  # Convert to a NumPy array
    img_array = np.expand_dims(img_array, axis=0) # Add a batch dimension
    img_array = img_array / 255.0  # Normalize the pixel values to [0, 1]
    return img_array

# Route for the homepage
@app.get("/", response_class=HTMLResponse)
async def main():
    with open("detect.html", "r", encoding="utf-8") as f:
        html_content = f.read()
    return HTMLResponse(content=html_content)

# Route for image prediction
@app.post("/predict/")
async def predict(file: UploadFile = File(...)):
    try:
        # Check if the uploaded image contains nails
        if not detect_nail(file.file):
            return JSONResponse(
                content={"error": "Kami tidak dapat mendeteksi kuku pada gambar yang Anda unggah. Pastikan Anda mengunggah gambar kuku yang benar"},
                status_code=400,
            )

        # Make a prediction using the CNN model
        img_array = preprocess_image(file.file)
        prediction_prob = cnn_model.predict(img_array)[0][0]

        # Determine the result based on the prediction
        if prediction_prob < 0.5:
            result = "Terdapat Indikasi Diabetes"
            confidence = (1 - prediction_prob) * 100
        else:
            result = "Terdeteksi Sehat"
            confidence = prediction_prob * 100

        return {
            "Hasil Deteksi": result,
            "Tingkat Persentase Deteksi": f"{confidence:.2f}%",
        }
    except Exception as e:
        return {"error": f"An error occurred: {str(e)}"}
