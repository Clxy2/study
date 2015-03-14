package cn.clxy.tools.swing.memo.service;

import java.util.List;

import cn.clxy.tools.swing.memo.domain.Tag;
import cn.clxy.tools.swing.memo.service.impl.TagServiceClientImpl;

import com.google.inject.ImplementedBy;

/**
 * Tag servie.
 * @author clxy
 */
@ImplementedBy(TagServiceClientImpl.class)
public interface TagService {

    List<Tag> searchAll();

    List<Tag> search(String name);

    void edit(Tag tag);

    void delete(Tag tag);
}
