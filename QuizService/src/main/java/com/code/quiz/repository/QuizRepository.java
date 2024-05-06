package com.code.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.quiz.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

}
