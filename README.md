# eshop project

Nama: Nezzaluna Azzahra  
NPM: 2406495741  
Kelas: AdvPro - B

## Module 2 - CI/CD & DevOps

### Reflection

1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.  
   Dalam pengerjaan exercise ini, beberapa code quality issues yang saya perbaiki mencakup integrasi code analysis dan code coverage reports. Cara saya resolve issues diantaranya adalah memastikan sinkronisasi antara konfigurasi lokal di build.gradle.kts dengan profil proyek di dashboard SonarCloud, terutama pada bagian sonar.projectKey dan sonar.organization untuk menghindari error analysis. Selain itu, dilakukan perbaikan pada konfigurasi plugin JaCoCo dengan mengaktifkan laporan dalam format XML agar data coverage dapat dibaca secara akurat oleh SonarCloud. Untuk menjaga stabilitas pipeline, task pengujian disesuaikan dengan menggunakan filter excludeTestsMatching("*FunctionalTest") agar functional yang membutuhkan browser tidak dijalankan di lingkungan server GitHub Actions yang tidak memiliki GUI.

Selain hal-hal terkait integrasi, beberapa code quality issues yang dilaporkan oleh sonarqube diantaranya aalah mencakup penggunaan field injection dan duplikasi literal string. Cara saya resolve issues tersebut adalah saya mengganti @Autowired pada private field menjadi constructor injection di kelas controller dan service, hal tersebut bertujuan untuk meningkatkan immutability serta memudahkan proses unit test. Selain itu, saya mendefinisikan constant untuk mengatasi duplikasi "redirect:/product/list" untuk memenuhi prinsip Clean Code terkait duplication. Terakhir, saya memperbaiki isu cakupan kode pada metode findAll dan findById dengan menambahkan unit test yang meaningful pada ProductServiceImplTest, sehingga memastikan logika bisnis ter-verify sepenuhnya dan code quality issues report di sonarqube ter-resolve dan code coverage meningkat.

2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!  
   Menurut saya, implementasi CI/CD saya saat ini sudah sepenuhnya memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD) karena seluruh workflow dari testing hingga deploy aplikasi telah ter-automate secara menyeluruh. Dalam segi CI, terpenuhi melalui penggunaan tiga workflow GitHub Actions, yaitu ci.yml untuk menjalankan testing secara otomatis, sonarqube.yml untuk melakukan pengecekan code quality serta pelaporan coverage JaCoCo, dan scorecard.yml untuk security scanning supply-chain setiap kali ada aktivitas push atau pull request. Sedangkan dalam segi CD, diimplementasikan melalui fitur "Auto-deploy on commit" di Koyeb yang secara otomatis mendeteksi perubahan pada branch main, kemudian melakukan build ulang menggunakan Dockerfile, dan meng-update aplikasi di prod env tanpa intervensi manual. Dengan demikian, integrasi antara quality scanning secara otomatis dan sistem deployment langsung ini menjamin bahwa setiap perubahan kode yang teruji dapat segera disuguhkan kepada user secara konsisten dan efisien.
