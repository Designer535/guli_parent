package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-23
 */
@RestController
@RequestMapping("/educms/crm-bannerAdmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page")long page, @PathVariable("limit")long limit){
        Page<CrmBanner> pageParam=new Page<>(page,limit);
        crmBannerService.page(pageParam,null);
        List<CrmBanner> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("items",records).data("total",total);
    }

    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        boolean save = crmBannerService.save(crmBanner);
        if(save){
            return R.ok().message("添加成功");
        }else{
            return R.fail().message("添加失败");
        }
    }

    @GetMapping("getBanner/{bannerId}")
    public R getBanner(@PathVariable("bannerId") String bannerId){
        CrmBanner banner = crmBannerService.getById(bannerId);
        return R.ok().data("banner",banner);
    }

    @DeleteMapping("deleteBanner/{bannerId}")
    public R deleteBanner(@PathVariable("bannerId")String bannerId){
        boolean b = crmBannerService.removeById(bannerId);
        if(b){
            return R.ok().message("删除成功");
        }else{
            return R.fail().message("删除失败");
        }
    }

    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        boolean b = crmBannerService.updateById(crmBanner);
        if(b){
            return R.ok().message("修改成功");
        }else{
            return R.fail().message("修改失败");
        }
    }

}

