//package cn.edu.jmu.cec.Test;
//
//import cn.edu.jmu.cec.common.Page;
//import cn.edu.jmu.cec.domain.ArtColumn;
//import cn.edu.jmu.cec.domain.Article;
//import cn.edu.jmu.cec.service.ArtColumnSevice;
//import cn.edu.jmu.cec.service.ArticleService;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//
///**
// * Created by yangjb on 2015/4/10.
// */
//@ContextConfiguration(locations = {"/applicationContext.xml"})
////@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@RunWith(SpringJUnit4ClassRunner.class)
//public class ArtServiceTest {
//    @Autowired
//    private ArticleService articleService;
//    @Autowired
//    private ArtColumnSevice artColumnSevice;
//
//    //进行单元测试
//    @org.junit.Test
//    public void testGetArticle() {
//        Article byId = articleService.getById(1);
//        System.out.println(byId.getAuthor());
//    }
//
//    @org.junit.Test
//    public void getArticle() {
////        ArtColumn byId = artColumnSevice.getById(1);
////        Page<Article> dataByPage = articleService.getAllByPage(1, byId, 10);
////        System.out.println(dataByPage.getTotalCount());
//    }
//}
