package com.electronics.store.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronics.store.enties.Category;
@Repository
public interface CategoryRepo extends JpaRepository<Category, String> {

	 Optional<List<Category>> findByTitleContaining(String title);
}
