package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Categories c WHERE c.level = :level")
    List<Category> findByLevel(int level);

    @Query("SELECT c.categories_id FROM Categories c WHERE c.name = :name")
    List<Long> findParentIdsByName(String name);

    @Query("SELECT c FROM Categories c WHERE c.name = :name AND c.level = 2")
    Optional<Category> findBrandByNameAndLevel(@Param("name") String name);

    @Query("SELECT c FROM Categories c WHERE c.name = :name AND c.parentCategory = :parent")
    Optional<Category> findByNameAndParentCategory(@Param("name") String name, @Param("parent") Category parentCategory);

    @Query("SELECT c FROM Categories c WHERE c.parentCategory = :parent")
    List<Category> findByParentCategory(@Param("parent") Category parentCategory);
}
