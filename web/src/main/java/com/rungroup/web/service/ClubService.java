package com.rungroup.web.service;

import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.models.Club;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();

    Club saveClub(ClubDto clubDto);

    ClubDto findClubById(long clubId);

    void updateClub(ClubDto club);

    void delete(Long clubId);

    List<ClubDto> searchClubs(String query);
}
