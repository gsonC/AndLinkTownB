package com.lianbi.mezone.b.httpresponse;

public class API {

	/**
	 * 成功返回有数据
	 */
	public static final int SUCCESS_EXIST = 0;
	/**
	 * 微信分享APP_ID
	 */
	public static final String APP_ID = "wxbe5a3f5342a2b178";
	/**
	 * 服务器异常
	 */
	public static final int FAILED_EXIST = 500;
	/**
	 * 表单错误
	 */
	public static final int ERROR_FORM_ERROR = 1002;

	/**
	 * 表示从查看二维码返回
	 */
	public static final int REQUEST_CODE_SCANNINGQR_RESULT = 1011;

	/**
	 * 需要登录错误
	 */
	public static final int ERROR_NEED_LOGIN = 3001;
	/**
	 * 软件使用协议
	 */
	public static final String PROTOCOL = "template/system/protocol.html";
	/**
	 * 支付协议
	 */
	public static final String PAYAGREEMENT = "template/system/payProtocol.html";
	/**
	 * 商户服务协议
	 */
	public static final String PROTOCOL_SHOP = "template/system/installProtocol.html";
	/**
	 * 线上服务
	 */
	public static final String TEMPLATE = "template/serveMarket/list.html?userId=";
	/**
     *  生产环境url
     */
//	public static final String ENVIRONMENTAL = "http://front.xylbn.cn:9003";//前置
//	public static final String TOSTORESERVICE = "http://mall.xylbn.cn";//到店服务
//	public static final String PAYQR = ENVIRONMENTAL+"/lincombFront/toOrderPay.do?state=";//支付二维码
//	public static final String INTELLIGENT_WIFI ="http://www.xylbn.cn/wcmv2/routerApplication/wifiIndex?businessId=";//智能wifi
//	public static final String WEB_MORESERVICE = "http://developer.lincomb.com/bsm/mall/index?plantId=";//更多服务
	/**
	 *  uat环境url
	 */
//	public static final String ENVIRONMENTAL = "http://139.196.214.16";//前置
//	public static final String TOSTORESERVICE = "http://139.196.214.16";//到店服务
//	public static final String PAYQR = ENVIRONMENTAL+"/lincombFront/toOrderPay.do?state=";//支付二维码
//	public static final String INTELLIGENT_WIFI ="http://139.196.214.16/wcmv2/routerApplication/wifiIndex?businessId=";//智能wifi
	/**
     *  测试环境url
     */
	public static final String ENVIRONMENTAL = "http://test.xylbn.cn";//前置
	public static final String TOSTORESERVICE = "http://172.16.103.153:9005";//到店服务
	public static final String PAYQR = ENVIRONMENTAL + "/lincombFront/toOrderPay.do?state=";//支付二维码
	public static final String INTELLIGENT_WIFI = "http://172.16.103.152:8090/wcmv2/routerApplication/wifiIndex?businessId=";//智能WIFI_TEST
	public static final String WEB_MORESERVICE = "http://172.16.103.14:8002/bsm/mall/toList?plantId=";//更多服务

	/**
	 * 总入口
	 */
	public static final String HOST = ENVIRONMENTAL + "/lincombFront/";
	/**
	 * 圣诞轰趴
	 */
	public static final String COMPANYEVENT = TOSTORESERVICE+"/wcm/serviceMall/enterScene/1";
	/**
	 * 订单管理
	 */
	public static final String ORDERMANAGER = TOSTORESERVICE+"/wcm/order/myOrders?storeId=";
	/**
	 * 场景征集令
	 */
	public static final String SCENECOLLECTION = TOSTORESERVICE+"/wcm/serviceMall/launchScene";
	/**
	 * 发布产品
	 */
	public static final String WEBCUR = HOST + "template/product/release.html";
	/**
	 *4.23	在线支付
	 */
	public static final String ONLINEPAY = "/wcm/store/onlinePay";
	/**
	 *桌位详情接口
	 */
	public static final String TABLETSSINFO = TOSTORESERVICE + "/wcm/tssTable/tableInfo";
	/**
	 *4.23	现金付款接口
	 */
	public static final String EDITORDER = TOSTORESERVICE + "/wcm/tssTable/editOrderStatus";
	/**
	 * 4.13	查询推送消息
	 */
	//public static final String PUSHMESSAGE =TOSTORESERVICE+"/wcm/serviceMall/BusinessId/getPushMessages";
	/**
	 * 4.13 查询推送消息
	 */
	public static final String PUSHMESSAGE ="getPushMessages";
	/**
	 *到店明细接口
	 */
	public static final String OREDR = TOSTORESERVICE + "/wcm/tssOrder/order";
	/**
	 * 产品管理
	 */
	public static final String TOSTORE_PRODUCT_MANAGEMENT = TOSTORESERVICE + "/wcm/tss/getAppAllProduct?storeId=";
	/**
	 * 微信商城
	 */
	public static final String TOSTORE_MODULE_WCM = TOSTORESERVICE + "/wcm/product/IntoBEndMenu?";
	/**
	 * 货源批发
	 */
	public static final String TOSTORE_Supply_Wholesale= TOSTORESERVICE + "/wcm/goto/showMenu?";
	/**
	 * 排队取号
	 */
	public static final String TOSTORE_Line_TakeNo = TOSTORESERVICE + "/wcm/qns/";
	/**
	 * 微信订单明细
	 */
	public static final String HOST_WECHAT_MALL = TOSTORESERVICE + "/wcm/order/showOrderDetl?";
	/**
	 * 货源订单明细
	 */
	public static final String HOST_SUPPLYGOODS_MALL = TOSTORESERVICE+"/wcm/sws/showOrderDetl?";
	/**
	 * 预约订单
	 */
	public static final String HOST_BOOK_MALL = TOSTORESERVICE+"/wcm/rss/showOrderDetl?";
	/**
	 * 预约订单
	 */
	public static final String FIND_SENCE = TOSTORESERVICE+"/wcm/discoverController/getDiscoverView";
	/**
	 * webURL介绍 clerk - 店员管理 finance - 财务管理 financialTransactions - 我要理财 manage
	 * - 经营收入 material - 我的资料 mySource - 我的货源 news - 我的资料 other - 其他功能 product -
	 * 产品管理 server - 服务商场 shops - 切换商铺 source - 货源商城 store - 我的店铺 sweep - 扫一扫
	 * trade - 交易管理 vip - 会员管理
	 */
	public static final String HOSTWEBCUR = HOST
			+ "template/introduce/index.html?type=";
	/**
	 * 订单列表
	 */
	public static final String ORDER = "order";
	/**
	 * 订单管理
	 */
	public static final String VIP = "orderManager";
	/**
	 * 产品管理
	 */
	public static final String TRADE = "productManager";
	/**
	 * 扫一扫
	 */
	public static final String SWEEP = "receipt";
	/**
	 * 服务商城
	 */
	public static final String SERVICESTORE = "serviceStore";
	/**
	 * 我的店铺
	 */
	public static final String STORE = "store";
	/**
	 * 订单明细
	 */
	public static final String ORDERDETAIL = "orderdetail";
	/**
	 * 切换商铺
	 */
	public static final String SHOPS = "shops";

	/**
	 * 收入明细
	 */
	public static final String INCOMEDETAIL = "income";
	/**
	 * 服务商场
	 */
	public static final String SERVER = "server";
	/**
	 * 其他功能
	 */
	public static final String OTHER = "other";
	/**
	 * 我的资料
	 */
	public static final String MATERIAL = "material";
	/**
	 * 我的消息
	 */
	public static final String NEWS = "news";
	/**
	 * 交易明细
	 */
	public static final String TRDATEDETAIL = "tradeDetail";
	/**
	 * 提现
	 */
	public static final String WITHDRAWDEPOSIT = "withdrawDeposit";
	/**
	 * 银行卡
	 */
	public static final String BANKCARD = "bankCard";
	/**
	 * 信息管理
	 */
	public static final String INFODETAILS = "infoDetails";
	/**
	 * 联璧官网
	 */
	public static final String MYCOMPANEY = "http://www.xylbn.cn/";
	/**
	 * 饼状柱状图
	 */
	public static final String URLJS = "template/financial/index.html?param=";
	/**
	 * 广告
	 */
	public static final String GETADVERT = "business/getBusBannerByType.do";
	/**
	 * 意见反馈
	 */
	public static final String ADDFEEDBACK = "feedBack/addFeedBack.do";
	/**
	 * 获取店铺员工
	 */
	public static final String GETSALESCLERKLIST = "salesclerk/getSalesclerkListByBusiness.do";
	/**
	 * 修改员工信息
	 */
	public static final String UPDATESALESCLERKBYID = "salesclerk/updateSalesclerkById.do";
	/**
	 * 删除员工信息
	 */
	public static final String DELSALESCLERKBYID = "salesclerk/delSalesclerkById.do";
	/**
	 * 新增员工信息
	 */
	public static final String ADDSALESCLERK = "salesclerk/addSalesclerk.do";
	/**
	 * 查询所有的会员卡等级
	 */
	public static final String GETASSOCIATORLEVELLIST = "associator/getAssociatorLevelList.do";

	/**
	 * 查询门店下的所有会员
	 */
	public static final String GETASSOCIATOR = "associator/getAssociator.do";
	/**
	 * 获取全部服务商城列表
	 */
	public static final String GETSERVICEMALLLIST = "serverMall/getServerMallList.do";
	/**
	 * 用户登录
	 */
	public static final String USErLOGIN = "user/userLogin.do";
	/**
	 * 获取用户详细
	 */
	public static final String GETUSEBYID = "/user/getUserById.do";
	/**
	 * 获取验证码
	 */
	public static final String SENDCODE = "user/registerMsgCode.do";
	/**
	 * 注册
	 */
	public static final String REGISTERUSER = "user/registerUser.do";
	/**
	 * 忘记密码
	 */
	public static final String FORGETPWD = "user/forgetPassword.do";

	/**
	 * 修改用户信息
	 */
	public static final String UPDATEUSEBYID = "user/updateUserById.do";
	/**
	 * 修改会员卡等级
	 */
	public static final String UPDATEASSOCIATORLEVEL = "associator/updateAssociatorLevel.do";
	/**
	 * 修改会员卡状态
	 */
	public static final String OPENORCLOSELEVEL = "associator/openOrCloseLevel.do";
	/**
	 * 获取我的消息列表
	 */
	public static final String GETMESSAGELIST = "message/getMessageList.do";
	/**
	 * 审核会员申请状态
	 */
	public static final String UPDATEAPPLYSTATUS = "associator/updateApplyStatus.do";
	/**
	 * 删除消息
	 */
	public static final String POSTDELMESSAGE = "message/delMessage.do";
	/**
	 * 删除货源
	 */
	public static final String DELETESHOP = "productSource/delProductSource.do";
	/**
	 * 获取货源列表
	 */
	public static final String GETPRODUCTSOURCELIST = "productSource/getProductSourceList.do";
	/**
	 * 新增货源
	 */
	public static final String ADDPRODUCTSOURCE = "productSource/addProductSource.do";
	/**
	 * B端切换店铺
	 */
	public static final String CHANGEBUSSINESSONAPP = "business/updateDefaultBusinessByUserId.do";
	/**
	 * 添加货源线下订单
	 */
	public static final String ADDPRODUCTSOURCEORDER = "order/addProductSourceOrder.do";
	/**
	 * 添加货源线下订单
	 */
	public static final String GETSWEEP = "sweep/getSweep.do";
	/**
	 * 获取购买的货源记录
	 */
	public static final String GETPRDUCTSOURCEORDERLISTBUBUSINESSID = "order/getProductSourceOrderListByBusinessId.do";
	/**
	 * 获取货源下单列表
	 */
	public static final String GETPRDUCTSOURCEORDERBYBUSINESS = "productSource/getProductSourceOrderByBusiness.do";
	/**
	 * 获取货源详细
	 */
	public static final String GETPRDUCTSOURCEDEBYID = "productSource/getProductSourceById.do";
	/**
	 * 查看订单详细
	 */
	public static final String GETORDERBYID = "order/getOrderById.do";
	/**
	 * <<<<<<< HEAD 获取店铺下的所有产品池数据
	 */
	public static final String GETPRODUCTPOOLBYBUSINESS = "productPool/getProductPoolByBusiness.do";
	/**
	 * 修改店铺产品池状态－－－修改产品商品服务后会新增一条与该产品一样的商品服务
	 */
	public static final String UPDATEPRODUCTPOOLSTATUS = "productPool/updateProductPoolStatus.do";
	/**
	 * B端修改订单状态---接受订单，拒绝订单
	 */
	public static final String UPDATEORDERSTATUS = "order/updateOrderStatu.do";
	/**
	 * 新增货源联系人
	 */
	public static final String ADDPRODUCTSOURCECONTACTS = "user/addProductSourceContacts.do";
	/**
	 * 显示货源联系人
	 */
	public static final String GETPRODUCTSOURCECONTACTS = "user/getProductSourceContacts.do";
	/**
	 * 取得用户下的店铺
	 */
	public static final String GETBUSINESSBYUSER = "business/getBusinessList.do";
	/**
	 * 修改密码
	 */
	public static final String UPDATEPASSWORD = "user/rePassword.do";
	/**
	 * 新增店铺B端
	 */
	public static final String ADDBUSINESSBYB = "business/addBusiness.do";
	/**
	 * 行业类别
	 */
	public static final String GETINDUSTRYLISTOFB = "industry/getIndustryList.do";
	/**
	 * 省份
	 */
	public static final String GETPROVINCELIST = "province/getProvinceList.do";
	/**
	 * 修改店铺介绍
	 */
	public static final String UPDATEBUSINESSINTRODUCE = "business/updateBusinessIntroduce.do";
	/**
	 * 发送设备信息
	 */
	public static final String POSTPHONECLIENTID = "device/sendDeviceInfo.do";
	/**
	 * 修改店铺LOGO
	 */
	public static final String UPDATEBUSINESSLOGO = "business/updateBusiness.do";
	/**
	 * 修改店铺联系人
	 */
	public static final String UPDATEBUSINESSCONTACTS = "business/updateBusiness.do";
	/**
	 * 修改店铺联系电话
	 */
	public static final String UPDATEBUSINESSPHONE = "business/updateBusiness.do";

	/**
	 * 修改店铺地址
	 */
	public static final String UPDATEBUSINESSADDRESS = "business/updateBusiness.do";

	/**
	 * 获取商铺详细信息
	 */
	public static final String GETBUSINESSINFO = "business/getBusinessInfo.do";

	/**
	 * 新增产品（B端）
	 */
	public static final String ADDPRODUCTINFOBYB = "product/addProductInfoByB.do";

	/**
	 * 获取店铺下的所有商品
	 */
	public static final String GETPRODUCTLISTBYBUSINESS = "product/getProductListByBusiness.do";
	/**
	 * 最近完成的10笔交易
	 */
	public static final String GETTENORDERLIST = "order/getTenOrderList.do";
	/**
	 * 查询门店下不同等级会员
	 */
	public static final String GETASSOCIACORBYBUSINESSANDLEVEL = "associator/getAssociatorByBusinessAndLevel.do";
	/**
	 * 删除会员
	 */
	public static final String DELASSOCIATOR = "associator/delAssociator.do";
	/**
	 * 修改会员
	 */
	public static final String UPDATEASSOCIATORr = "associator/updateAssociator.do";
	/**
	 * 根据电话号码检索会员
	 */
	public static final String GETASSOCIATORBUPHONE = "associator/getAssociatorByPhone.do";
	/**
	 * 修改信息状态
	 */
	public static final String UPDATEMESSAGE = "message/updateMessage.do";
	/**
	 * 获取货源特惠
	 */
	public static final String GETPRODUCTSOURCEPREFERENCE = "productSource/getProductSourcepReference.do";
	/**
	 * 删除银行卡
	 */
	public static final String DELBANK = "finance/deleteboundCard.do";
	/**
	 * 修改银行卡密码
	 */
	public static final String UPDATEBANKPASSWORD = "finance/updateboundCardPassWd.do";
	/**
	 * 绑定银行卡
	 */
	public static final String ADDBANK = "finance/boundCard.do";
	/**
	 * 获取银行卡
	 */
	public static final String GETBANKLIST = "finance/queryCardInfo.do";
	/**
	 *  获取收入明细
	 */
	public static final String GETINCOME = "finance/queryAmtFlow.do";
	/**
	 * * 获取银行列表
	 */
	public static final String GETALLBANK = "finance/queryAllBankInfo.do";
	/**
	 * * 获取经营总收入
	 */
	public static final String GETCOUNTBYBUSINESS = "transaction/getCountByBusiness.do";
	/**
	 * * 获取某天经营总收入
	 */
	public static final String GETCOUNTBYDAY = "transaction/getCountByDay.do";
	/**
	 * * 获取本年每月收入数据
	 */
	public static final String GETAMOUNTBYMONTH = "transaction/getAmountByMonth.do";
	/**
	 * * 获取上周每日收入数据
	 */
	public static final String GETAMOUNTBYWEEK = "transaction/getAmountByWeek.do";
	/**
	 * * 获取在线每天收入明细
	 */
	public static final String GETDETAILONLINE = "transaction/getDetailOnline.do";
	/**
	 * *获取到店每天收入明细
	 */
	public static final String GETDETAILOFFLINE = "transaction/getDetailoffline.do";
	/**
	 * *获取线下每天不同时间段的经营收入
	 */
	public static final String GETOFFLINECOUNTBYTWOHOURS = "transaction/getOfflineCountByTwoHours.do";
	/**
	 * *获取线上每天不同时间段的经营收入
	 */
	public static final String GETONLINECOUNTBYTWOHOURS = "transaction/getOnlineCountByTwoHours.do";
	/**
	 * *获取线下经营总收入
	 */
	public static final String GETOFFLINECOUNTBYBUSINESS = "transaction/getOfflineCountByBusiness.do";
	/**
	 * * 获取线上经营总收入
	 */
	public static final String GETONLINECOUNTBYBUSINESS = "transaction/getOnlineCountByBusiness.do";
	/**
	 * *获取店铺下不同时间不同状态的订单
	 */
	public static final String GETORDERLISTBYBUSINESSIDDATESTATU = "order/getOrderListByBusinessIdDateStatu.do";
	/**
	 * 提现
	 */
	public static final String WITHDRAW = "finance/doWithdraw.do";
	/**
	 * 订单明细
	 */
	public static final String GETWITHDRAWBYUSERID = "finance/queryStoreWithdrawRecord.do";
	/**
	 * *获取上周的线下经营总收入
	 */
	public static final String GETOFFLINELASTWEEKCOUNT = "transaction/getOfflineLastWeekCount.do";
	/**
	 * *获取上周的线上经营总收入
	 */
	public static final String GETONLINELASTWEEKCOUNT = "transaction/getOnlineLastWeekCount.do";
	/**
	 * *获取某月线下经营总收入
	 */
	public static final String GETOFFLINECOUNTBYMONTH = "transaction/getOfflineCountByMonth.do";
	/**
	 * *获取某月线上经营总收入
	 */
	public static final String GETONLINECOUNTBYMONTH = "transaction/getOnlineCountByMonth.do";
	/**
	 * 忘记银行卡密码
	 */
	public static final String FORGETPASSWORD = "finance/restPayPassWd.do";
	/**
	 * 找回密码
	 */
	public static final String FINDPASSWORD = "finance/restPayPassWd.do";
	/**
	 * 获取版本信息
	 */
	public static final String GETEDITION = "edition/getEdition.do";
	/**
	 * 发送短信验证码
	 */
	public static final String SENDUPDATEPASSWORDCODE = "finance/sendMsg.do";
	/**
	 * 验证短信验证码
	 */
	public static final String CHECKUPDATEPASSWORDCODE = "finance/checkSendMsg.do";
	/**
	 * 扫码支付
	 */
	public static final String SWEEPPAY = "/pay/addSweepPay.do";
	/**
	 * 刷卡支付
	 */
	public static final String PUSHCARD = "sweep/pushCard.do";
	/**
	 * 查询微信订单状态
	 */
	public static final String ORDERQUERY = "sweep/orderQuery.do";
	/**
	 * APP订单明细
	 */
	public static final String OREDRINFO = "order/queryOrder.do";

	/**
	 * APP删除订单明细
	 */
	public static final String UPDATEORDER = "order/updateOrder.do";
	/**
	 * 今日用户收入
	 */
	public static final String QUERYACCOUNTTODAYINCOME = "finance/queryAccountTodayIncome.do";
	/**
	 * 店铺总额
	 */
	public static final String SHOPACCOUNT = "finance/queryStoreTotalBalance.do";

	/**
	 * 余额
	 */
	public static final String YUE = "finance/queryStoreBalance.do";
	/**
	 * 当前店铺总收入
	 */
	public static final String ALLINCOMENUM = "finance/queryStoreToalIncome.do";

	/**
	 * 当前店铺今日收入
	 */
	public static final String TODAYINCOME = "finance/queryStoreTodayIncome.do";
	/**
	 * 我的总收入
	 */
	public static final String MyAllIncome = "finance/ queryAccountToalIncome.do";

	/**
	 * 我的账户总额
	 */
	public static final String MyAllIncome2 = " finance/queryAccountBalance.do";
	/**
	 * 体现中金额
	 */
	public static final String StoreWithdraw = " finance/queryStoreWithdrawAmount.do";
	/**
	 * 今日收入
	 */
	public static final String TODAYDETAIL = "finance/queryStoreIncomeDetail.do";
	/**
	 * 每日收入明细
	 */
	public static final String EVERYDAYINCOME = "finance/queryAccountDailyIncomeDetail.do";
	/**
	 * 冻结中金额
	 */
	public static final String FREEZINGAMOUNT = "";
	/**
	 * 冻结中金额
	 */
	public static final String FINANCIALOFFICEAMOUNT = "finance/queryFinIncomeInfoRate.do";
	/**
	 * 新增产品
	 */
	public static final String AddPRODUCTT = "product/addProduct.do";
	/**
	 * 修改积分产品
	 */
	public static final String CHANGERODUCT = "product/updateProduct.do";
	/**
	 * 修改产品
	 */
	public static final String QueryProduct = "product/query.do";
	/**
	 * 积分商品查询
	 */
	public static final String QUERYPOINTPRODUCT = "product/queryProduct.do";
	/**
	 * 从微信产品库选择
	 */
	public static final String QUERYFROMWEIXINSHOP = "product/wcmSelectProduct.do";

	/**
	 * 会员列表
	 */
	public static final String MEMBERSLIST = "businessVIP/vipWXList.do";
	/**
	 * 添加会员和修改会员信息
	 */
	public static final String ADDORUPDATEMEMBER = "businessVIP/addOrUpdateVip.do";
	/**
	 * 查询会员详情
	 */
	public static final String MEMBERDETAILS = "businessVIP/vipInfoDetail.do";
	/**
	 * 获取会员标签
	 */
	public static final String MEMBERLABELLIST = "vipLabel/vipLabelList.do";
	/**
	 * 获取呼叫列表
	 */
	public static final String CALLLIST = "getTssCallTypeList.do";

	/**
	 * 4.20	查询店铺的待支付信息
	 */
	public static final String GETONPAIDUUID = "getUnPaidOrder.do";
	/**
	 * 增加会员标签
	 */
	public static final String ADDVIPLABEL = "vipLabel/addVipLabel.do";
	/**
	 * 增加呼叫标签
	 */
	public static final String ADDCALLTAG= "createTssCallType.do";
	/**
	 * 删除会员标签
	 */
	public static final String DELETELABEL = "vipLabel/delVipLabel.do";
	/**
	 * 删除呼叫内容
	 */
	public static final String DELETECALLLABEL = "deleteTssCallType.do";
	/**
	 * 删除积分商品
	 */
	public static final String DELETEPRODUCT = "product/deleteProduct.do";
	/**
	 * 删除会员标签
	 */
	public static final String UPDATEPRODUCT = "updateProduct.do";
	/**
	 * 查询会员协议
	 */
	public static final String MEMBERAGREEMENT = "business/queryBusProtocol.do";
	/**
	 * 添加会员协议
	 */
	public static final String ADDMEMBERAGREEMENT = "business/addBusProtocol.do";
	//	public static final String MEMBERLABELLIST = "vipLabel/vipLabelList.do";
	/*
	* 获取会员优惠券列表
	* */
	public static final String COUPONS_LIST = "coupon/queryStoreCoup.do";
	/*
	* 查询店铺优惠券使用详情列表
	* */
	public static final String COUPON_USING_DETAIL_LIST = "coupon/queryCoupDetail.do";
	/*
	* 发送优惠券短信
	* */
	public static final String SEND_NEW_COUPON = "msg/sendCoupMsg.do";
	/*
	* 会员消费记录
	* */
	public static final String RECORDSOFCONSUMPTION = "vipConsum/consumList.do";
	/*
	* 会员积分记录
	* */
	public static final String INTEGRALRECORD = "vipIntegral/vipIntegralList.do";
	///////////////////////////////////////////////////////会员管理部分接口////////////////////////////////////////////////////////////////////////////
	/* 店铺会员等级折扣比例修改* */
	public static final String UPDATEDISTRIC = "businessVip/updateDistrictByBusinessId.do";

	/* 店铺会员等级和折扣比例查询* */
	public static final String MEMBERVIPINFO = "businessVip/queryDistrictByBusinessId.do";
	/* 会员分类列表* */
	public static final String MEMBER_CATEGORY_LIST = "vipType/vipTypeList.do";
	/* 添加会员类别* */
	public static final String ADDMEMBER_CATEGORY_LIST = "vipType/addVipType.do";
	/* 会员类别详情* */
	public static final String MEMBER_TYPEDETAIL_LIST = "vipType/detailVipType.do";
	/* 修改会员类别* */
	public static final String MEMBER_TYPEUPDATE_LIST = "vipType/updVipType.do";
	/* 短信营销管理统计* */
	public static final String MARKETINGSMS_STATISTICS_LIST = "msg/querySendMsgStatistic.do";
	/* 短信营销发送详情* */
	public static final String MARKETINGSMS_SENDDETAIL_LIST = "msg/querySendMsgDetail.do";
	/* 新建营销短信* */
	public static final String MARKETINGSMS_SENDMSG_LIST = "msg/sendMsg.do";
	/* 可供购买的短信套餐* */
	public static final String MARKETINGSMS_MSGPACKAGE_LIST = "msg/msgPackage.do";
	/* 查询短信模板* */
	public static final String MARKETINGSMS_QUERYTEMPLATE_LIST = "message/queryAllTemplate.do";
	/* 群发短信 * */
	public static final String MARKETINGSMS_BULK_LIST = "message/smsBulkSend.do";
	/* 群发结果查询**/
	public static final String MARKETINGSMS_QUERYRESULTS_LIST = "message/queryBulkResult.do";
	/* 查询今日剩余额度、今日总额度、今日已用额度**/
	public static final String QUERY_AMT_CONFIG = "finance/queryAmtConfig.do";
	/* 提现状态 */
	public static final String QUERY_WITHDRAW_STATS = "finance/queryWithdrawStats.do";
	/* 商圈吆喝接口 */
	public static final String ADD_LEAGUES_YELL = "business/addBusinessDynamic.do";
	/* 查询商圈动态 */
	public static final String QUERY_LEAGUES_DYNAMIC = "business/queryBusinessDynamic.do";
	/* 商圈统计 */
	public static final String QUERY_LEAGUES_DISTRICTCOUNT = "business/districtCount.do";
	/* 店铺列表 */
	public static final String QUERY_LEAGUES_BUSINESSLIST = "business/selectBusinessListByAreaCode.do";
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 首页--会员营销
	 */
	public static final String SHOUYE_SHOPVIP_MARKET = "order/assemblyResult.do";
	/**
	 * 首页--销量排行
	 */
	public static final String SHOUYE_SHOPVIP_CONSUMPTIONCURVE = "order/getPerHourseAmt.do";
	/**
	 * 首页--消息记录
	 */
	public static final String SHOUYE_SHOPVIP_ORDERCOUNT = "getCountPaidPushOrder";
	/**
	 * 首页--实时消费
	 */
	public static final String SHOUYE_SHOPVIP_CONSUMPTION = "/tssOrder/order";
	/**
	 * 首页--销量排行
	 */
	public static final String SHOUYE_SHOPVIP_SALERANK = "/tss/getProductSalsRank";
	/**
	 * 首页--省市区
	 */
	public static final String SHOUYE_SHOPVIP_PROVINCE = "download/pcdJson.do";
}
