package com.tuwien.semanticweb;

import com.tuwien.semanticweb.service.SparqlService;

import java.util.Scanner;

public class App {

    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);
        String option = "";

        System.out.println("Welcome to our SPARQL CLI Java Application");
        System.out.println("******************************************");

        while (!option.equals("0")) {

            showOptions();

            System.out.print("Select option number: ");
            option = scanner.nextLine();

            switch (Integer.parseInt(option)) {
                case 1: SparqlService.listFaculties();
                    break;
                case 2: SparqlService.listInstitutesByFaculty();
                    break;
                case 3: SparqlService.listMembersByInstitute();
                    break;
                case 4: SparqlService.listCoursesOfTeacher();
                    break;
                case 5: SparqlService.listEmailByMember();
                    break;
                case 6: SparqlService.listCoursesBySemester();
                    break;
                case 7: SparqlService.listCourseDetails();
                    break;
            }
        }
    }

    private static void showOptions() {
        System.out.println();
        System.out.println("Available options: ");
        System.out.println("    (1) List all faculties");
        System.out.println("    (2) List all institutes by faculty");
        System.out.println("    (3) List all members by institute");
        System.out.println("    (4) List all courses by teacher");
        System.out.println("    (5) Get email of member");
        System.out.println("    (6) List courses by semester");
        System.out.println("    (7) List details of course");
        System.out.println("    (0) Exit application");
        System.out.println();

    }
}
