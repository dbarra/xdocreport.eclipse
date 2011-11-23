package org.dynaresume.dao;

import org.dynaresume.domain.core.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GroupDao extends CrudRepository<Group, Long>,
		PagingAndSortingRepository<Group, Long> {

}