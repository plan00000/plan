package com.plan.frame.system.dto.login.dictionary;

import java.util.List;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName ResDictonaryDto
 * @Date 2020/6/15 11:24
 */
public class ResDictinaryDto {
    List<DictionaryDto> dictionaryDtoList;

    public List<DictionaryDto> getDictionaryDtoList() {
        return dictionaryDtoList;
    }

    public void setDictionaryDtoList(List<DictionaryDto> dictionaryDtoList) {
        this.dictionaryDtoList = dictionaryDtoList;
    }
}
