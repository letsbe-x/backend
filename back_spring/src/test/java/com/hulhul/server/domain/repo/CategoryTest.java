package com.hulhul.server.domain.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.category.CategoryRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class CategoryTest {

	@Autowired
	CategoryRepo categoryRepo;

	@Test
	public void DI_테스트() {
		assertThat(categoryRepo, is(notNullValue()));
	}

	@Test
	public void 카테고리_목록_불러오기() {
		// set
		String[] testNames = { "테스트1", "테스트2", "테스트3" };

		// when
		long preSize = categoryRepo.count();

		// givien
		for (String testName : testNames) {
			categoryRepo.save(Category.builder().name(testName).build());
		}

		// when
		List<Category> categoryList = categoryRepo.findAll();

		// then
		assertThat(categoryRepo.count(), is(preSize + testNames.length));
	}
}
