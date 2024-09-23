package com.exercise.boot;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.exercise.boot.repository")
@ComponentScan(basePackages = "com.exercise.boot")
@EntityScan(basePackages = "com.exercise.boot.entity")
//@EnableDiscoveryClient -- While registering in Eureka server we need to use @EnableDiscoveryClient
public class AbcBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbcBankApplication.class, args);
        XWPFDocument document = new XWPFDocument();

        // Output file
        String fileName = "Full_Stack_Developer_CV.docx";

        // Add Title
        XWPFParagraph title = document.createParagraph();
        XWPFRun titleRun = title.createRun();
        titleRun.setText("[Your Name]");
        titleRun.setBold(true);
        titleRun.setFontSize(20);
        titleRun.addCarriageReturn();
        titleRun.setText("Full Stack Developer");
        titleRun.addCarriageReturn();
        titleRun.setText("[Phone Number] | [Email Address] | [LinkedIn Profile] | [GitHub Profile]");

        // Add Section: Professional Summary
        XWPFParagraph summaryTitle = document.createParagraph();
        XWPFRun summaryTitleRun = summaryTitle.createRun();
        summaryTitleRun.setBold(true);
        summaryTitleRun.setText("Professional Summary");

        XWPFParagraph summaryContent = document.createParagraph();
        XWPFRun summaryContentRun = summaryContent.createRun();
        summaryContentRun.setText(
                "Dynamic and results-oriented Full Stack Developer with 3 years of experience in the Financial Services domain "
                        + "at Capgemini. Proficient in Java, Spring Boot, React, and performance testing tools, with a proven track record "
                        + "of delivering high-quality software solutions. Skilled in full software development lifecycle, including Agile "
                        + "practices, BDD, TDD, and performance optimization using Gatling and JMeter. Adept at leading automation frameworks "
                        + "with Cucumber and Selenium. Strong experience in CI/CD pipelines using Jenkins, version control with Git, and API "
                        + "documentation with Swagger."
        );

        // Add Section: Technical Skills
        XWPFParagraph skillsTitle = document.createParagraph();
        XWPFRun skillsTitleRun = skillsTitle.createRun();
        skillsTitleRun.setBold(true);
        skillsTitleRun.setText("Technical Skills");

        addBulletPoint(document, "Languages & Frameworks:");
        addBulletPoint(document, "Java 8, Java 17, Spring, Spring Boot, Core Java, React, JavaScript");
        addBulletPoint(document, "Testing & Automation:");
        addBulletPoint(document, "Cucumber, Selenium, BDD, TDD, Pact, Performance Testing (Gatling, JMeter)");
        addBulletPoint(document, "DevOps & CI/CD:");
        addBulletPoint(document, "Jenkins, Git, Docker, Kubernetes (optional)");
        addBulletPoint(document, "Tools & Platforms:");
        addBulletPoint(document, "Swagger, OCP, Postman");
        addBulletPoint(document, "Databases:");
        addBulletPoint(document, "PostgreSQL, MySQL, H2");

        // Add Section: Professional Experience
        XWPFParagraph experienceTitle = document.createParagraph();
        XWPFRun experienceTitleRun = experienceTitle.createRun();
        experienceTitleRun.setBold(true);
        experienceTitleRun.setText("Professional Experience");

        XWPFParagraph experienceContent = document.createParagraph();
        XWPFRun experienceContentRun = experienceContent.createRun();
        experienceContentRun.setText(
                "Capgemini | Full Stack Developer\n" +
                        "July 2021 – Present | Financial Services Domain\n" +
                        "- Developed full-stack applications using Java 8/17, Spring Boot, and React, enhancing customer experiences for " +
                        "financial services clients.\n" +
                        "- Collaborated with cross-functional teams in Agile environments to implement key features using BDD and TDD.\n" +
                        "- Designed and automated testing frameworks using Cucumber and Selenium, significantly reducing manual testing time by 40%.\n" +
                        "- Led performance testing initiatives using Gatling and JMeter, improving application load time and ensuring scalability.\n" +
                        "- Integrated CI/CD pipelines using Jenkins and Git, ensuring smooth deployment processes across development, testing, and production environments.\n" +
                        "- Worked on RESTful API development, documentation with Swagger, and API testing using Postman."
        );

        // Save the document
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            document.write(out);
            System.out.println("Word document created: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to add bullet points
    private static void addBulletPoint(XWPFDocument document, String text) {
        XWPFParagraph bullet = document.createParagraph();
        bullet.setIndentationLeft(500);
        XWPFRun bulletRun = bullet.createRun();
        bulletRun.setText("• " + text);
    }

}
