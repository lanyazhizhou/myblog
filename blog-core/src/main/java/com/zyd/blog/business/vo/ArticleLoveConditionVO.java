package com.zyd.blog.business.vo;

import com.zyd.blog.business.entity.ArticleLove;
import com.zyd.blog.framework.object.BaseConditionVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author lanyazhizhou@gmail.com
 * @version 1.0
 * @website https://lanyazhizhou.com
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleLoveConditionVO extends BaseConditionVO {
	private ArticleLove articleLove;
}

