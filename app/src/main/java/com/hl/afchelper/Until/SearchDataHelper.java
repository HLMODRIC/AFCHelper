package com.hl.afchelper.Until;

public class SearchDataHelper {
    public String ShowData(String tag) {
        switch (tag) {

            /*
            *首页
             */
            case "线路信息":
                return "select * from basic where id < 100";
            case "部门信息":
                return "select * from basic where id between 100 and 199";
            case "公司制度":
                return "select * from basic where id between 200 and 299";
            case "会议纪要":
                return "select * from basic where id between 300 and 399";
            case "下发文件":
                return "select * from basic where id between 400 and 499";
            case "培训内容":
                return "select * from basic where id between 500 and 599";
            case "工作相关":
                return "select * from basic where id between 600 and 699";
            case "考核条例":
                return "select * from basic where id between 700 and 799";
            case "应急预案":
                return "select * from basic where id between 800 and 899";

            /*
            *理论
             */
            case "AFC系统介绍":
                return "select * from theory where id < 100";
            case "TVM各模块介绍":
                return "select * from theory where id between 100 and 199";
            case "GATE各模块介绍":
                return "select * from theory where id between 200 and 299";
            case "POST各模块介绍":
                return "select * from theory where id between 300 and 399";
            case "软件操作相关":
                return "select * from theory where id between 400 and 499";
            case "网络通信相关":
                return "select * from theory where id between 500 and 599";
            case "硬件操作相关":
                return "select * from theory where id between 600 and 699";
            case "TVM各模块详解":
                return "select * from theory where id between 700 and 799";
            case "GATE各模块详解":
                return "select * from theory where id between 800 and 899";
            case "POST各模块详解":
                return "select * from theory where id between 900 and 999";

            /*
            *维护
             */
            case "TVM故障分析":
                return "select * from repair where id < 100";
            case "GATE故障分析":
                return "select * from repair where id between 100 and 199";
            case "POST故障分析":
                return "select * from repair where id between 200 and 299";
            case "软件故障分析":
                return "select * from repair where id between 300 and 399";
            case "通信故障分析":
                return "select * from repair where id between 400 and 499";
            case "保养相关":
                return "select * from repair where id between 500 and 599";
            case "其他故障":
                return "select * from repair where id between 600 and 699";
            case "TVM疑难故障分析":
                return "select * from repair where id between 700 and 799";
            case "GATE疑难故障分析":
                return "select * from repair where id between 800 and 899";
            case "POST疑难故障分析":
                return "select * from repair where id between 900 and 999";


            /*
            *视频
             */
            case "TVM各模块":
                return "select * from radio where id < 100";
            case "GATE各模块":
                return "select * from radio where id between 100 and 199";
            case "POST各模块":
                return "select * from radio where id between 200 and 299";
            case "其它":
                return "select * from radio where id between 300 and 399";
            case "radio_item_5":
                return "select * from radio where id between 400 and 499";
            case "radio_item_6":
                return "select * from radio where id between 500 and 599";

            /*
            *查询
             */
            case  "CHS代码查询":
                return "select * from chs_code";
            case  "BNR代码查询":
                return "select * from bnr_code";
            case  "MBC110代码查询":
                return "select * from mbc_code";
            case  "刷卡代码查询":
                return "select * from card_code";
            case  "车站IP查询":
                return "select * from ip_code";
            case  "螺丝查询":
                return "select * from screw_code";


            default:
                return "select * from basic where id between 100 and 200";
        }
    }

    public String SearchData(String tag) {
            switch (tag) {
                case "线路信息":
                    return "select * from basic where id < 100 and content like ?";
                case "部门信息":
                    return "select * from basic where id between 100 and 199 and content like ?";
                case "公司制度":
                    return "select * from basic where id between 200 and 299 and content like ?";
                case "会议纪要":
                    return "select * from basic where id between 300 and 399 and content like ?";
                case "下发文件":
                    return "select * from basic where id between 400 and 499 and content like ?";
                case "培训内容":
                    return "select * from basic where id between 500 and 599 and content like ?";
                case "工作相关":
                    return "select * from basic where id between 600 and 699 and content like ?";
                case "应急预案":
                    return "select * from basic where id between 700 and 799 and content like ?";

                /*
            *理论
             */
                case "AFC系统介绍":
                    return "select * from theory where id < 100 and content like ?";
                case "TVM系统介绍":
                    return "select * from theory where id between 100 and 199 and content like ?";
                case "GATE系统介绍":
                    return "select * from theory where id between 200 and 299 and content like ?";
                case "POST系统介绍":
                    return "select * from theory where id between 300 and 399 and content like ?";
                case "软件操作相关":
                    return "select * from theory where id between 400 and 499 and content like ?";
                case "网络通信相关":
                    return "select * from theory where id between 500 and 599 and content like ?";
                case "硬件操作相关":
                    return "select * from theory where id between 600 and 699 and content like ?";
                case "TVM各模块详解":
                    return "select * from theory where id between 700 and 799 and content like ?";
                case "GATE各模块详解":
                    return "select * from theory where id between 800 and 899 and content like ?";
                case "POST各模块详解":
                    return "select * from theory where id between 900 and 999 and content like ?";

            /*
            *维护
             */
                case "TVM故障分析":
                    return "select * from repair where id < 100 and content like ?";
                case "GATE故障分析":
                    return "select * from repair where id between 100 and 199 and content like ?";
                case "POST故障分析":
                    return "select * from repair where id between 200 and 299 and content like ?";
                case "软件故障分析":
                    return "select * from repair where id between 300 and 399 and content like ?";
                case "通信故障分析":
                    return "select * from repair where id between 400 and 499 and content like ?";
                case "保养相关":
                    return "select * from repair where id between 500 and 599 and content like ?";
                case "其他故障":
                    return "select * from repair where id between 600 and 699 and content like ?";
                case "TVM疑难故障分析":
                    return "select * from repair where id between 700 and 799 and content like ?";
                case "GATE疑难故障分析":
                    return "select * from repair where id between 800 and 899 and content like ?";
                case "POST疑难故障分析":
                    return "select * from repair where id between 900 and 999 and content like ?";


            /*
            *视频
             */
                case "TVM各模块":
                    return "select * from radio where id < 100 and content like ?";
                case "GATE各模块":
                    return "select * from radio where id between 100 and 199 and content like ?";
                case "POST各模块":
                    return "select * from radio where id between 200 and 299 and content like ?";
                case "其它":
                    return "select * from radio where id between 300 and 399 and content like ?";
                case "radio_item_5":
                    return "select * from radio where id between 400 and 499 and content like ?";
                case "radio_item_6":
                    return "select * from radio where id between 500 and 599 and content like ?";

            /*
            *查询
             */
                case  "CHS代码查询":
                    return "select * from chs_code where content like ?";
                case  "BNR代码查询":
                    return "select * from bnr_code where content like ?";
                case  "MBC110代码查询":
                    return "select * from mbc_code where content like ?";
                case  "刷卡代码查询":
                    return "select * from card_code where content like ?";
                case  "车站IP查询":
                    return "select * from ip_code where content like ?";
                case  "螺丝查询":
                    return "select * from screw_code where content like ?";
                default:
                    return "select * from radio where id between 500 and 599 and content like ?";
            }
        }

}
