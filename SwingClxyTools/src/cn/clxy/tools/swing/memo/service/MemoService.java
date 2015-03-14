package cn.clxy.tools.swing.memo.service;

import java.util.List;
import java.util.Map;

import cn.clxy.tools.swing.memo.domain.Memo;
import cn.clxy.tools.swing.memo.service.impl.MemoServiceClientImpl;

import com.google.inject.ImplementedBy;

/**
 * Memo servie.
 * @author clxy
 */
@ImplementedBy(MemoServiceClientImpl.class)
public interface MemoService {

    /**
     * Edit a memo.
     * @param memo
     */
    void edit(Memo memo);

    /**
     * Search Memo by condition.
     * @param condition
     * @return
     */
    List<Memo> search(Map<String, Object> condition);

    /**
     * Delete memo
     * @param deleteMemos
     */
    void delete(List<Memo> deleteMemos);

    void merge(List<Memo> memos, List<Memo> deleteMemos);
}
