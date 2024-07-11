package com.web.service;

import com.web.domain.Notice;
import com.web.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public Page<Notice> findAllNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    @Override
    public Notice findNoticeById(Long id) {
        return noticeRepository.findById(id).orElse(null);
    }
}