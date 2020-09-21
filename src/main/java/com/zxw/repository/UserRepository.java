package com.zxw.repository;

import com.zxw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    int countBySexAndGroupNum(String sex, String groupNum);

    @Query("SELECT sex AS sex,count(sex) AS chartNum FROM User  WHERE groupNum = ?1 GROUP BY sex")
    List<Map<String, Object>> getChartNum(String groupNum);
}
