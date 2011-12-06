package org.dynaresume.dao.mock;

import java.util.ArrayList;
import java.util.List;

import org.dynaresume.dao.ProjectDao;
import org.dynaresume.domain.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("projectDao")
public class MockProjectDao extends AbstractDaoMock<Project> implements ProjectDao {

	protected Project clone(Project project) {
		Project newProject = new Project();
		newProject.setId(project.getId());
		newProject.setName(project.getName());
		newProject.setURL(project.getURL());
		newProject.setDescription(project.getDescription());
		return newProject;
	}

	public Page<Project> findByNameLike(String name, Pageable pageable) {
		name = Utils.getCriteria(name);
		int pageSize = pageable.getPageSize();
		int pageIndex = pageable.getOffset();
		Iterable<Project> allProjects = findAll();
		List<Project> filteredList = new ArrayList<Project>();
		for (Project project : allProjects) {
			if (isProjectOK(project, name)) {
				filteredList.add(project);
			}
		}
		long totalSize = filteredList.size();
		List<Project> paginatedList = new ArrayList<Project>();
		for (int i = pageIndex; i < pageIndex + pageSize && i < totalSize; i++) {
			Project project = filteredList.get(i);
			paginatedList.add(project);
		}
		return new PageImpl<Project>(paginatedList, pageable, totalSize);
	}

	private boolean isProjectOK(Project project, String label) {
		if (label == null) {
			return true;
		}
		if (project.getName() == null) {
			return false;
		}
		return project.getName().toUpperCase().startsWith(label.toUpperCase());
	}

}
