package com.code.question.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.code.question.model.Question;
import com.code.question.model.QuestionWrapper;
import com.code.question.model.Response;
import com.code.question.repository.QuestionDao;

@Service
public class QuestionService {

	@Autowired
	QuestionDao dao;

	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {

		try {
			return new ResponseEntity<>(dao.findByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> addQuestion(Question question) {

		dao.save(question);
		return new ResponseEntity<>("Question added successfully!!", HttpStatus.CREATED);

	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQuestions) {

		List<Integer> questionids = dao.findRandomQuestionsByCategory(category, numQuestions);

		return new ResponseEntity<>(questionids, HttpStatus.OK);

	}

	public ResponseEntity<String> deleteQuestionById(Integer id) {

		dao.deleteById(id);
		return new ResponseEntity<>("Question has been deleted", HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
		List<Question> questionsFromDB = new ArrayList<>();
		List<QuestionWrapper> questionsForUser = new ArrayList<>();

		for (Integer id : questionIds) {
			questionsFromDB.add(dao.findById(id).get());
		}
		for (Question q : questionsFromDB) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),
					q.getOption3(), q.getOption4());
			questionsForUser.add(qw);
		}

		return new ResponseEntity<>(questionsForUser, HttpStatus.OK);

	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		int score = 0;
		for (Response response : responses) {
			Question question = dao.findById(response.getId()).get();
			if (response.getResponse().equals(question.getRightAnswer())) {// if response answer is equal to right
																			// answer
				score++;
			}
		}

		return new ResponseEntity<Integer>(score, HttpStatus.OK);

	}

}
