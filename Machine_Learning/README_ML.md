# NailScan.ai - Detect Diabetes from Nail Conditions 🤖💅

NailScan.ai leverages Machine Learning to detect diabetes through nail condition analysis. This repository contains the code and resources for building and deploying a **Convolutional Neural Network (CNN)** model trained on nail images to classify them as *Healthy* or *Diabetes*. Let's use technology to make healthcare more accessible and proactive! 🌟

<p>
   <img src="[Images/Training_Loss.png](https://drive.google.com/file/d/1PUoY2ERCss064wgpovNXu6PkaRHD3SaB/view?usp=drive_link)" alt="Training Loss" style="width:48%; margin-right:4%;">
</p>
---

## 🌟 Features
- **AI-powered Detection**: Classify nail conditions with high accuracy.
- **Interactive Dashboard**: Use a Streamlit app for easy user interaction.
- **Cloud Integration**: Real-time predictions with cloud-based ML models.
- **Future Ready**: Designed for scalability and multi-condition support.

---

## 🛠 Tools & Technologies
The project is built using a range of powerful tools and libraries:
- **Google Colaboratory**: Experimentation and model training.
- **TensorFlow** & **Keras**: Neural network architecture and training.
- **Scikit-learn**: Data preprocessing and evaluation.
- **OpenCV (cv2)**: Image preprocessing.
- **Matplotlib** & **Seaborn**: Visualization of metrics and trends.
- **Streamlit**: Web-based application interface for predictions.
- **NumPy** & **Pandas**: Efficient data manipulation and analysis.

---

## 📂 Dataset Overview
Our dataset comprises **nail images** categorized into two classes:
1. **Healthy**: Nails without diabetes indicators.
2. **Diabetes**: Nails with signs associated with diabetes.

### Dataset Structure
The dataset is organized as follows:



### Source
Data was collected with permission from healthcare institutions and processed for training and validation. 

---

## 🚀 Machine Learning Model
### Architecture
A **Convolutional Neural Network (CNN)** architecture was chosen for its efficiency in image classification tasks.

### Performance Metrics
- **Accuracy**: 90%
- **Precision**: 88%
- **Recall**: 92%

#### Training & Validation Results:
<p>
   <img src="Images/Training_Loss.png" alt="Training Loss" style="width:48%; margin-right:4%;">
   <img src="Images/Training_Accuracy.png" alt="Training Accuracy" style="width:48%;">
</p>

#### Confusion Matrix:
<p>
   <img src="Images/Confusion_Matrix.png" alt="Confusion Matrix"/>
</p>

---

## 🔧 Application Workflow
1. **Image Upload**: Users upload a nail image via the **NailScan.ai** interface.
2. **Image Processing**: The image is processed and sent to the trained ML model hosted in the cloud.
3. **Prediction**: The model classifies the nail as *Healthy* or *Diabetes*.
4. **Results**: The app displays the classification along with a confidence score.

---

## 📋 How to Use
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/username/NailScan.ai.git



