package com.danielfinal.finalproject.repositories;

import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag getTagByName (String name);

    Optional<Tag> findTagByName (String name);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE tbl_tag SET message_id = ?1 WHERE name = ?2",
            nativeQuery = true
    )
    void updateMessageIdByTagName(long messageId, String name);

}
