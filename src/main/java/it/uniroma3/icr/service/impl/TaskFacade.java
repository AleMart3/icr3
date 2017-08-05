package it.uniroma3.icr.service.impl;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.StudentDao;
import it.uniroma3.icr.dao.TaskDao;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.Task;

@Service
public class TaskFacade {

	@Autowired
	private TaskDao taskDao;
	@Autowired
	private ResultFacade resultFacade;

	public void addTask(Task t) {
		taskDao.save(t);
	}

	public Task retrieveTask(long id) {
		return this.taskDao.findOne(id);
	}

	public List<Task> retrieveAllTask() {
		return this.taskDao.findAll();
	}

	public List<Task> findTaskByStudent(Long id) {
		return this.taskDao.findByStudentId(id);
	}

	public Task assignTask(Student s, Long id) {
		Task task = null;
		if(id == null){
			List<Task> tasksByStudent = this.taskDao.findByStudentId(s.getId());
			List<Task> taskList = this.taskDao.findByStudentIsNull();
			for(int i=0; i<taskList.size();i++){
				if(!tasksByStudent.contains(taskList.get(i))){
					task = taskList.get(i);
					task.setStudent(s);
					Calendar calendar = Calendar.getInstance();
					java.util.Date now = calendar.getTime();
					java.sql.Timestamp date = new java.sql.Timestamp(now.getTime());
					task.setStartDate(date);

					break;
				}
			}
			if(task!=null){
				s.addTask(task);
				this.taskDao.save(task);
			}
		}else{
			task = this.taskDao.findOne(id);
		}

		return task;
	}

	public void updateEndDate(Task t) {
		taskDao.updateEndDate(t);
	}	

	//console

	public List<Object> studentsProductivity() {
		return this.taskDao.studentsProductivity();
	}

	public List<Object> taskTimes() {
		return this.taskDao.taskTimes();
	}

	public List<Object> majorityVoting() {
		return this.taskDao.majorityVoting();
	}

	public List<Object> symbolAnswers() {
		return this.taskDao.symbolAnswers();
	}

	public List<Object> voting() {
		return this.taskDao.voting();
	}

	public List<Object> symbolsMajorityAnswers() {
		return this.taskDao.symbolsMajorityAnswers();
	}

	public List<Object> correctStudentsAnswers() {
		return this.taskDao.correctStudentsAnswers();
	}

	public void createTask(Job job, Integer number, Boolean word,Task task) {
		for(int i = 0; i<job.getNumberOfStudents() ; i++){
			int batchNumber = 0;
			for(int r = 0 ; r<number ; r++) {
				
				if ((r % job.getTaskSize())==0) {

					task = new Task();
					task.setBatch(batchNumber);
					task.setJob(job);
					job.addTask(task);
					this.addTask(task);
					batchNumber++;
				}
				if(word){
					List<Image> images =job.getWords().get(r).getImages();

					for(int j = 0; j<images.size() ; j++ ){
						Result result = new Result();
						result.setImage(images.get(j));
						result.setTask(task);
						this.resultFacade.addResult(result);				
					}
				}else{
					Image j = job.getImages().get(r);
					Result result = new Result();
					result.setImage(j);
					result.setTask(task);
					this.resultFacade.addResult(result);
				}
			}
		}
	}

}
