from flask import Flask, request, jsonify
import tensorflow as tf
import numpy as np
import json
import nltk
from nltk.stem import WordNetLemmatizer

# Download necessary NLTK data files
nltk.download('punkt', download_dir='./nltk_data')
nltk.download('wordnet', download_dir='./nltk_data')

# Initialize Flask app
app = Flask(__name__)

# Load pre-trained model
model = tf.keras.models.load_model('chatbot_modelV2.h5')

# Load words and classes
with open('augmentedDatasetCombined.json', 'r') as json_file:
    data = json.load(json_file)
    words = data['words']
    classes = data['classes']

lemmatizer = WordNetLemmatizer()

def clean_up_sentence(sentence):
    """Tokenize and lemmatize the input sentence."""
    sentence_words = nltk.word_tokenize(sentence)
    sentence_words = [lemmatizer.lemmatize(word.lower()) for word in sentence_words]
    return sentence_words

def bow(sentence, words, show_details=True):
    """Return bag of words array: 0 or 1 for each word in the bag that exists in the sentence."""
    sentence_words = clean_up_sentence(sentence)
    bag = [0] * len(words)
    for s in sentence_words:
        for i, w in enumerate(words):
            if w == s:
                bag[i] = 1
                if show_details:
                    print(f"found in bag: {w}")
    return np.array(bag)

def predict_class(sentence, model):
    """Predict the class of the input sentence."""
    bag = bow(sentence, words, show_details=False)
    res = model.predict(np.array([bag]))[0]
    ERROR_THRESHOLD = 0.25
    results = [[i, r] for i, r in enumerate(res) if r > ERROR_THRESHOLD]

    # Sort by strength of probability
    results.sort(key=lambda x: x[1], reverse=True)
    return [{"intent": classes[r[0]], "probability": str(r[1])} for r in results]

@app.route('/predict', methods=['POST'])
def chatbot_response():
    """API endpoint to get chatbot response."""
    data = request.get_json()
    if 'message' not in data:
        return jsonify({"error": "Message key is missing"}), 400

    user_message = data['message']
    intents = predict_class(user_message, model)

    if not intents:
        return jsonify({"response": "I'm sorry, I don't understand."})
    
    # Placeholder response logic
    tag = intents[0]['intent']
    response = f"This is a response for the intent '{tag}' (confidence: {intents[0]['probability']})"
    return jsonify({"response": response})

@app.route('/')
def home():
    return "Chatbot API is running!"

# Run Flask app
if __name__ == "__main__":
    app.run(host='0.0.0.0', port=8080)
