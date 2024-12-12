package com.capstone.diacare.utils

object ResultPrecautionAndSolution {
    fun getDiabetesPrecaution(value: Double): String {
        val returnValue = if (value <= 60) {
            "Tetap jaga gaya hidup sehat dengan konsumsi makanan bergizi dan aktivitas fisik ringan seperti berjalan kaki 30 menit per hari."
        } else if (value > 60 && value <= 70) {
            "Hindari konsumsi gula berlebih dan perbanyak makan buah serta sayuran segar."
        } else if (value > 70 && value <= 80) {
            "Perbaiki pola makan dengan mengurangi karbohidrat sederhana, dan tingkatkan konsumsi makanan berserat."
        } else if (value > 80 && value <= 90) {
            "Tingkatkan aktivitas fisik secara rutin, hindari kebiasaan buruk seperti merokok, dan kelola stres dengan baik."
        } else {
            "Hindari makanan manis sepenuhnya, minum cukup air, dan konsultasikan pola makan dengan ahli gizi."
        }
        return returnValue
    }

    fun getDiabetesRisk(value: Double): String {
        val returnValue = if (value <= 60) {
            "Indikasi diabetes hampir tidak terdeteksi. Namun, penting untuk selalu memantau kesehatan tubuh."
        } else if (value > 60 && value <= 70) {
            "Ada kemungkinan kecil diabetes, tetapi indikasinya masih sangat ringan."
        } else if (value > 70 && value <= 80) {
            "Kemungkinan diabetes mulai muncul, meskipun gejalanya belum terlihat nyata."
        } else if (value > 80 && value <= 90) {
            "Terdapat indikasi jelas diabetes. Perhatian serius diperlukan untuk mencegah komplikasi lebih lanjut."
        } else {
            "Kemungkinan diabetes mulai muncul, meskipun gejalanya belum terlihat nyata."
        }
        return returnValue
    }

    fun getDiabetesSuggestion(value: Double): String {
        val resultValue = if (value <= 60) {
            "Lakukan pemeriksaan kesehatan rutin minimal setahun sekali untuk pencegahan dini."
        } else if (value > 60 && value <= 70) {
            "Mulailah mencatat asupan harian Anda untuk memantau pola makan secara lebih baik."
        } else if (value > 70 && value <= 80) {
            "Pertimbangkan untuk berkonsultasi dengan dokter dan melakukan tes gula darah lebih mendalam."
        } else if (value > 80 && value <= 90) {
            "Segera periksakan diri Anda ke dokter untuk mendapatkan diagnosa lebih akurat."
        } else {
            "Segera buat janji dengan dokter spesialis untuk mendapatkan penanganan medis dan rencana pengobatan yang sesuai."
        }
        return resultValue
    }

    fun getHealthyPrecaution(value: Double): String {
        val returnValue = if (value <= 60) {
            "Lakukan olahraga ringan seperti berjalan kaki atau yoga, dan hindari konsumsi makanan cepat saji."
        } else if (value > 60 && value <= 70) {
            "Jaga pola makan seimbang dengan konsumsi makanan kaya protein, vitamin, dan serat."
        } else if (value > 70 && value <= 80) {
            "Lanjutkan kebiasaan olahraga rutin dan pastikan untuk tidur cukup setiap malam (6-8 jam)."
        } else if (value > 80 && value <= 90) {
            "Perbanyak konsumsi air putih dan hindari kebiasaan buruk seperti begadang atau konsumsi alkohol."
        } else {
            "Tetap jaga gaya hidup aktif, konsumsi makanan bernutrisi, dan hindari stres berlebihan."
        }
        return returnValue
    }

    fun getHealthyRisk(value: Double): String {
        val returnValue = if (value <= 60) {
            "Kondisi sehat terdeteksi, namun tetap ada potensi kecil untuk diabetes. Tetap waspada dan jaga kesehatan."
        } else if (value > 60 && value <= 70) {
            "Tidak ada indikasi diabetes, dan kondisi tubuh terdeteksi sehat. Risiko sangat rendah."
        } else if (value > 70 && value <= 80) {
            "Kondisi tubuh stabil dan sehat. Tidak ada tanda-tanda diabetes yang terdeteksi."
        } else if (value > 80 && value <= 90) {
            "Tubuh Anda dalam kondisi sangat sehat dengan tingkat kepercayaan tinggi."
        } else {
            "Kondisi tubuh Anda sangat prima. Tidak ada risiko diabetes terdeteksi dengan akurasi maksimal."
        }
        return returnValue
    }

    fun getHealthySuggestion(value: Double): String {
        val resultValue = if (value <= 60) {
            "Pastikan Anda memeriksakan kesehatan secara rutin untuk mempertahankan kondisi ini."
        } else if (value > 60 && value <= 70) {
            "Tetap lakukan pemeriksaan kesehatan setiap enam bulan untuk memastikan kondisi tubuh tetap optimal."
        } else if (value > 70 && value <= 80) {
            "Gunakan hasil ini sebagai motivasi untuk mempertahankan pola hidup sehat."
        } else if (value > 80 && value <= 90) {
            "Nikmati kondisi tubuh yang prima ini, namun tetap lakukan pengecekan kesehatan secara berkala."
        } else {
            "Terus lakukan pemeriksaan kesehatan secara berkala sebagai langkah preventif untuk masa depan."
        }
        return resultValue
    }
}