package com.telusko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class App {
	public static void main(String[] args) {
		Configuration conf = new Configuration().configure().addAnnotatedClass(Author.class)
				.addAnnotatedClass(Question.class);
		SessionFactory sf = conf.buildSessionFactory();
		Session session = sf.openSession();

		// Uncomment this to generate the data in the
		// database. Change connection details in the cfg file to your local db details.
		// insertQuestionns(session);

		// comment out this block if generating questions
		Scanner scanner = new Scanner(System.in);
		String input = "";
		while (!input.contains("quit")) {
			System.out.println("Enter total mark for the exam");
			input = scanner.nextLine();
			int totalMark = 0;
			int reminder = 0;

			try {
				totalMark = Integer.parseInt(input);
				reminder = totalMark % 10;
				if (reminder != 0) {
					throw (new NumberFormatException());
				}
				if (reminder == 0) {
					int numberOfQuestionsInEachSection = totalMark / 10;
					generateExamQuestions(session, numberOfQuestionsInEachSection);
				}
			} catch (NumberFormatException e) {
				if (input.contains("quit")) {
					System.out.println("Quiting excecution!!");
				} else {
					System.out.println("Please make sure that you enter an integer that is divisible by 10");
				}
			}

		}

		scanner.close();
		// comment end

		session.close();
		System.exit(0);
		;
	}

	private static void generateExamQuestions(Session session, int numberOfQuestionsInEachSection) {

		Query<Question> q = session.createQuery("from Question");
		List<Question> fetchedQuestions = q.list();
		List<Question> easyQuestions = new ArrayList<Question>();
		List<Question> intermediateQuestions = new ArrayList<Question>();
		List<Question> veryDifficultQuestions = new ArrayList<Question>();

		for (Question question : fetchedQuestions) {
			if (question.getLevel() == 1) {
				easyQuestions.add(question);
			} else if (question.getLevel() == 2) {
				intermediateQuestions.add(question);
			} else {
				veryDifficultQuestions.add(question);
			}
		}

		List<Question> outputQuestions = new ArrayList<Question>();
		List<Question> outputEasyQuestions = easyQuestions.subList(0, numberOfQuestionsInEachSection);
		List<Question> outputIntermediateQuestions = intermediateQuestions.subList(0, numberOfQuestionsInEachSection);
		List<Question> outputDifficultQuestions = veryDifficultQuestions.subList(0, numberOfQuestionsInEachSection);

		outputQuestions.addAll(outputEasyQuestions);
		outputQuestions.addAll(outputIntermediateQuestions);
		outputQuestions.addAll(outputDifficultQuestions);

		Object[] questionArray = outputQuestions.toArray();
		System.out.println("Exam Questions");
		System.out.println("==============");

		System.out.println("Section 1");
		System.out.println("=========");
		for (int i = 0; i < numberOfQuestionsInEachSection; i++) {
			System.out.println(i + 1 + ") " + (Question) questionArray[i]);
		}

		System.out.println("Section 2");
		System.out.println("=========");
		for (int i = numberOfQuestionsInEachSection; i < numberOfQuestionsInEachSection * 2; i++) {
			System.out.println(i + 1 + ") " + (Question) questionArray[i]);
		}
		System.out.println("Section 3");
		System.out.println("=========");

		for (int i = numberOfQuestionsInEachSection * 2; i < numberOfQuestionsInEachSection * 3; i++) {
			System.out.println(i + 1 + ") " + (Question) questionArray[i]);
		}
		System.out.println("========================End========================\n\n");

	}

	private static void insertQuestionns(Session session) {
		session.beginTransaction();

		Question[] questions = new Question[30];

		Collection<Question> level1Questions = new ArrayList<Question>();
		Collection<Question> level2Questions = new ArrayList<Question>();
		Collection<Question> level3Questions = new ArrayList<Question>();

		Author[] authors = new Author[3];

		for (int i = 0; i < 3; i++) {
			authors[i] = new Author();
			authors[i].setAid(1000 + i);
		}
		authors[0].setName("Teju");
		authors[1].setName("Kamil");
		authors[2].setName("Prafula");

		for (int i = 0; i < 30; i++) {
			questions[i] = new Question();
			questions[i].setQid(500 + i);
			questions[i].setQuestion("Question" + i);
			level1Questions.add(questions[i]);
		}

		for (int i = 0; i < 10; i++) {
			questions[i].setLevel(1);
			questions[i].setMark(2);
			questions[i].setAuthor(authors[0]);
			level2Questions.add(questions[i]);
		}

		for (int i = 10; i < 20; i++) {
			questions[i].setLevel(2);
			questions[i].setMark(3);
			questions[i].setAuthor(authors[1]);
			level3Questions.add(questions[i]);
		}

		for (int i = 20; i < 30; i++) {
			questions[i].setLevel(3);
			questions[i].setMark(5);
			questions[i].setAuthor(authors[2]);
		}

		authors[0].setQuestions(level1Questions);
		authors[1].setQuestions(level2Questions);
		authors[2].setQuestions(level3Questions);

		for (int i = 0; i < authors.length; i++) {
			session.save(authors[i]);
		}

		for (int i = 0; i < questions.length; i++) {
			session.save(questions[i]);
		}
		session.getTransaction().commit();
		session.close();
	}
}
