package ${targetPackage};

import lombok.Data;

import java.io.Serializable;

<#include "header.ftl">
public @Data class ${domainObjectClassName}Query implements Serializable {

    /** 关键字 */
    private String keyword;
    /** 主键 */
    private String id;
    /** 包含主键集 */
    private String[] ids;
    /** 排除主键集 */
    private String[] excludeIds;

}
