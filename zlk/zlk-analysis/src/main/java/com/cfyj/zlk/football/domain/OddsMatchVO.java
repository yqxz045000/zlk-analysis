package com.cfyj.zlk.football.domain;

import java.util.Date;

import lombok.Data;

/**
 * PO:persistant object 持久对象,可以看成是与数据库中的表相映射的java对象。最简单的PO就是对应数据库中某个表中的一条记录，
 * 多个记录可以用PO的集合。PO中应该不包含任何对数据库的操作。
 * 
 * VO:value object值对象。通常用于业务层之间的数据传递，和PO一样也是仅仅包含数据而已。但应是抽象出的业务对象,可以和表对应,也可以不,
 * 这根据业务的需要.个人觉得同DTO(数据传输对象),在web上传递。
 * 
 * DAO:data access object 数据访问对象，是一个sun的一个标准j2ee设计模式 ．此对象用于访问数据库。通常和PO结合使用，DAO中包含了各种数据库的操作方法。通过它的方法,结合PO对数据库进行相关的操作。夹在业务逻辑与数据 库资源中间。配合VO, 
提供数据库的CRUD操作...
 * 
 * BO(business object) 业务对象
	从业务模型的角度看,见UML元件领域模型中的领域对象。封装业务逻辑的java对象,通过调用DAO方法,结合PO,VO进行业务操作。这个对象可以包括一个或多个其它的对象。 
 * 
 * POJO:plain ordinary java object 简单无规则java对象,我个人觉得它和其他不是一个层面上的东西,VO和PO应该都属于它。
 * 
 * 
 * DTO:Data Transfer Object（数据传输对象）DTO 是一组需要跨进程或网络边界传输的聚合数据的简单容器。它不应该包含业务逻辑，
并将其行为限制为诸如内部一致性检查和基本验证之类的活动。注意，不要因实现这些方法而导致 DTO 依赖于任何新类。在设计数据传输对象时，
您有两种主要选择：使用一般集合；或使用显式的 getter 和 setter 方法创建自定义对象。
 * 
１．VO是用new关键字创建，由GC回收的。 
　　PO则是向数据库中添加新数据时创建，删除数据库中数据时削除的。并且它只能存活在一个数据库连接中，断开连接即被销毁。 
２．VO是值对象，精确点讲它是业务对象，是存活在业务层的，是业务逻辑使用的，它存活的目的就是为数据提供一个生存的地方。 
　　PO则是有状态的，每个属性代表其当前的状态。它是物理数据的对象表示。使用它，可以使我们的程序与物理数据解耦，并且可以简化对象数据与物理数据之间的转换。 
３．VO的属性是根据当前业务的不同而不同的，也就是说，它的每一个属性都一一对应当前业务逻辑所需要的数据的名称。 
　　PO的属性是跟数据库表的字段一一对应的。PO对象需要实现序列化接口。
 * @author Exception
 *
 */
@Data
public class OddsMatchVO {
	
	private Long matchId;
	
	private Long qtid ;
	
	private Long stageMatchId;
	
	private String stageName;
	
	private String hn ;
	
	private String gn ;
	
	private Date matchTime;
	
	private Integer matchType; // -1：完 ，0：未开赛，1:上半场，2：中场，3：下半场， -12:腰斩，-10：取消，-11：待,4:加时
	
	
	

}
