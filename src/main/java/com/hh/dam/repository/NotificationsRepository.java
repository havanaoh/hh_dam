package com.hh.dam.repository;

import com.hh.dam.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {

    List<Notifications> findAllByMember_MemberIdAndIsReadFalse(int memberId);
}
