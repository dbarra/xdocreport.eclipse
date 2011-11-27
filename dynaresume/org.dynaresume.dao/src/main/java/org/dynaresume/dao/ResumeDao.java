package org.dynaresume.dao;

import org.dynaresume.domain.hr.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ResumeDao extends PagingAndSortingRepository<Resume, Long> {

	public Page<Resume> findByOwnerFirstNameAndOwnerLastName(String firstName,
			String lastName, Pageable pageable);
}
