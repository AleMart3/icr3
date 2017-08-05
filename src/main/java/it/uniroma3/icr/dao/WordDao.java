package it.uniroma3.icr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.*;
@Repository
public interface WordDao extends JpaRepository<Word, Long>, WordDaoCustom{
		
	}

