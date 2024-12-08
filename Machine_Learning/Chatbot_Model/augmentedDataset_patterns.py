import json
from nltk.corpus import wordnet
import random
# Membaca file JSON
with open("intentsDiabetesCombined.json", "r") as file:
    dataset = json.load(file)

# Contoh melihat data
for item in dataset:
    print(item)

# Fungsi sinonim untuk augmentasi patterns
def synonym_replacement(sentence):
    words = sentence.split()
    new_sentence = []
    for word in words:
        synonyms = wordnet.synsets(word)
        if synonyms:
            synonym = random.choice(synonyms).lemmas()[0].name()
            new_sentence.append(synonym)
        else:
            new_sentence.append(word)
    return " ".join(new_sentence)

# Augmentasi patterns
for item in dataset:
    augmented_patterns = []
    for pattern in item['patterns']:
        augmented_patterns.append(synonym_replacement(pattern))
    item['patterns'].extend(augmented_patterns)

with open("augmentedDatasetCombined.json", "w") as file:
    json.dump(dataset, file, indent=4)

print("Dataset augmentasi berhasil disimpan!")
