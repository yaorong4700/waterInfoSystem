package com.clarion.worksys.util;

public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_USER_RIGHTS = "sessionUserRights";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)).*"; // 不对匹配该值的访问路径拦截（正则）
	public static final String XML_HEADER = "<?xml version= \"1.0\" encoding=\"utf-8\"?>";
	public enum DepartmentEnum{
		ALL("公司所有部门",0),
		DEVELOP("開発部門",1),
		NONDEVELOP("開発以外",2),
		GCSJB("工程设计部",9),
		RJKFB("软件开发部",10),
		GZKFB("构造开发部",11),
		DQKFB("电气开发部",12),
		KFPZBZB("开发品质保证部",13),
		SPKFS("商品开发室",14),
		JYGLB("经营管理部",15),
		GXSJB("构想设计部",16),
		YJQHB("原价企画部",17),
		KFZYBSJK("开发支援部设计课",35),
		KFZYBKFGLK("开发支援部开发管理课",36),
		KFTKS("开发统括室",40),
		CHINAODM("中国ODM制品开发部",41);
		
		private String name;
		private int id;
		
		private DepartmentEnum(String name, int id){
			this.name = name;
			this.id = id;
		}
		
		public static String getName(int id){
			for (DepartmentEnum de : DepartmentEnum.values()){
				if (de.getId() == id){
					return de.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}
	}
	
	public enum StateEnum{
		ONJOB("在职",1),
		VACATION("休假",2),
		DEMISSION("离职",3);
		
		private String name;
		private int id;
		
		private StateEnum(String name, int id){
			this.name = name;
			this.id = id;
		}
		
		public static String getName(int id){
			for (StateEnum se : StateEnum.values()){
				if (se.getId() == id){
					return se.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}
	}
	public enum StateEnumCT{
		ONJOB("在職",1),
		VACATION("休職",2),
		DEMISSION("退職",3),
		MOBILE("異動",4);
		
		private String name;
		private int id;
		
		private StateEnumCT(String name, int id){
			this.name = name;
			this.id = id;
		}
		
		public static String getName(int id){
			for (StateEnumCT se : StateEnumCT.values()){
				if (se.getId() == id){
					return se.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}
	}
	public enum RoleEnum{
		NORMAL("普通权限",1),
		SUPER("超级管理员",2),
		CHIEF("职制人员",3),
		WORKNUM("工数担当",4);
		
		private String name;
		private int id;
		
		private RoleEnum(String name, int id){
			this.name = name;
			this.id = id;
		}
		
		public static String getName(int id){
			for (RoleEnum re : RoleEnum.values()){
				if (re.getId() == id){
					return re.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}
	}
	
	public enum AbilityEnum{
		CHIEF("职制",1),
		DIRECTOR("主任/组长",2),
		ABOVE3YEARS("担当(3年以上)",3),
		UNDER3YEARS("担当(3年以下)",4);
		
		private String name;
		private int id;
		
		private AbilityEnum(String name, int id){
			this.name = name;
			this.id = id;
		}
		
		public static String getName(int id){
			for (AbilityEnum re : AbilityEnum.values()){
				if (re.getId() == id){
					return re.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}
	}
	
	public enum StaffSortEnum{
		STAFF("公司员工",1),
		OUTSIDE("外驻",2),
		DISPATCH("派遣",3);
		
		private String name;
		private int id;
		
		private StaffSortEnum(String name, int id){
			this.name = name;
			this.id = id;
		}
		
		public static String getName(int id){
			for (StaffSortEnum re : StaffSortEnum.values()){
				if (re.getId() == id){
					return re.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}
	}
	
	public enum ProjectStateEnum{
		BEGIN("开发中","1"),
		OVER("开发完了","2"),
		PAUSE("开发中止","3"),
		STOP("保留","4");
		
		private String name;
		private String id;
		
		private ProjectStateEnum(String name, String id){
			this.name = name;
			this.id = id;
		}
		
		public static String getName(String id){
			for (ProjectStateEnum se : ProjectStateEnum.values()){
				if (se.getId() == id){
					return se.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public String getId() {
			return id;
		}
	}
	
	public enum ProjectStateEnumCT{
		BEGIN("開発中","1"),
		OVER("開発完了","2"),
		PAUSE("開発中止","3"),
		STOP("保留","4");
		
		private String name;
		private String id;
		
		private ProjectStateEnumCT(String name, String id){
			this.name = name;
			this.id = id;
		}
		
		public static String getName(String id){
			for (ProjectStateEnum se : ProjectStateEnum.values()){
				if (se.getId() == id){
					return se.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public String getId() {
			return id;
		}
	}
	
}
