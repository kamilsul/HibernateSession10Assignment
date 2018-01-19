package com.telusko;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Question {
	@Id
	private int qid;
	private String question;
	private int level;
	private int mark;

	@ManyToOne
	Author author;

	@Override
	public String toString() {
		String questionDetail = "Question [qid=" + qid + ", question=" + question + ", level=" + level + ", mark="
				+ mark;
		if (author != null) {
			questionDetail = questionDetail + ", Author Id " + author.getAid() + ", author name: " + author.getName();
		}
		questionDetail = questionDetail + "]";
		return questionDetail;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	// public Author getAuthor() {
	// return author;
	// }
	//
	// public void setAuthor(Author author) {
	// this.author = author;
	// }

}
