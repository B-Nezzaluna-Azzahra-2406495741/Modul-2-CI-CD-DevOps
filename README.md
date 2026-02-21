# eshop project

Nama: Nezzaluna Azzahra  
NPM: 2406495741  
Kelas: AdvPro - B

## Module 2 - CI/CD & DevOps

### Reflection

1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.  
   Dalam pengerjaan exercise ini, beberapa code quality issues yang saya perbaiki mencakup integrasi code analysis dan code coverage reports. Strategi perbaikan yang saya terapkan adalah memastikan sinkronisasi antara konfigurasi lokal di build.gradle.kts dengan profil proyek di dashboard SonarCloud, terutama pada bagian sonar.projectKey dan sonar.organization untuk menghindari error pindaian. Selain itu, dilakukan perbaikan pada konfigurasi plugin JaCoCo dengan mengaktifkan laporan dalam format XML agar data coverage dapat dibaca secara akurat oleh SonarCloud. Untuk menjaga stabilitas pipeline, task pengujian disesuaikan dengan menggunakan filter excludeTestsMatching("*FunctionalTest") agar functional yang membutuhkan browser tidak dijalankan di lingkungan server GitHub Actions yang tidak memiliki GUI.

2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!  
   Implementasi saat ini menurut saya telah memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD) secara fungsional. Proses integrasi yang terjadi secara kotinu/terus-menerus (CI) sudah berjalan otomatis melalui GitHub Actions yang memicu eksekusi test suite dan analisis kualitas kode setiap kali ada perubahan pada codebase. Penggunaan alat bantu seperti Gradle dan JaCoCo memastikan bahwa setiap kode yang masuk telah terverifikasi dan diukur kualitasnya secara terprogram sebelum dimerge. Sementara itu, aspek Continuous Deployment tercapai melalui penggunaan layanan PaaS menggunakan Koyeb yang secara otomatis melakukan deployment kode ke server produksi segera setelah tahap verifikasi pada workflow selesai. Dengan alur ini, saya dapat mengirimkan fitur baru ke live sever atau production dengan lebih cepat.
