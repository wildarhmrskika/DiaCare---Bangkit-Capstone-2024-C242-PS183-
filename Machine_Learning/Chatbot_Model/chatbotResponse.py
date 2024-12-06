import numpy as np
from nltk.stem import WordNetLemmatizer
import nltk

nltk.download('punkt', download_dir='Bangkit/NLTK Path')
nltk.data.path.append('Bangkit/NLTK Path')
lemmatizer = WordNetLemmatizer()

# Fungsi untuk membersihkan input
def clean_up_sentence(sentence):
    sentence_words = nltk.word_tokenize(sentence)
    return [lemmatizer.lemmatize(word.lower()) for word in sentence_words]

# Fungsi untuk membuat Bag of Words
def bow(sentence, words):
    sentence_words = clean_up_sentence(sentence)
    bag = [0] * len(words)
    for s in sentence_words:
        for i, w in enumerate(words):
            if w == s:
                bag[i] = 1
    return np.array(bag)

# Fungsi untuk memprediksi kelas intent
def predict_class(sentence, model, words, classes):
    bow_vector = bow(sentence, words)
    predictions = model.predict(np.array([bow_vector]))[0]
    ERROR_THRESHOLD = 0.25
    results = [[i, p] for i, p in enumerate(predictions) if p > ERROR_THRESHOLD]
    results.sort(key=lambda x: x[1], reverse=True)
    return [{"intent": classes[r[0]], "probability": str(r[1])} for r in results]

# Fungsi untuk mendapatkan respons dari chatbot
def chatbot_response(msg, model, intents, words, classes):
    ints = predict_class(msg, model, words, classes)
    tag = ints[0]['intent']
    for i in intents['intents']:
        if i['tag'] == tag:
            return np.random.choice(i['responses'])
