package com.dev.ecsite.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.dev.ecsite.dao.BuyItemDAO;
import com.dev.ecsite.dao.LoginDAO;
import com.dev.ecsite.dto.BuyItemDTO;
import com.dev.ecsite.dto.LoginDTO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ログイン認証処理 Login.jspからログインID、ログインパスワードを受け取り DBへ問い合わせを行います。
 *
 * @author internous
 * @param loginUserId
 * @param loginPassword
 *
 * @return result
 */
public class LoginAction extends ActionSupport implements SessionAware {

	/* ログインID */
	private String loginUserId;
	/**ログインパスワード */
	private String loginPassword;

	/* 処理結果を格納 */
	private String result;
	/* ログイン情報を格納 */
	public Map<String, Object> session;

	/* ログイン情報取得DAO インスタンス*/
	private LoginDAO loginDAO = new LoginDAO();
	/* ログイン情報格納IDTO インスタンス*/
	private LoginDTO loginDTO = new LoginDTO();
	/* アイテム情報を取得 インスタンス*/
	private BuyItemDAO buyItemDAO = new BuyItemDAO();

	/* 実行メソッド*/
	public String execute() {

		result = ERROR;
		// ログイン実行
		loginDTO = loginDAO.getLoginUserInfo(loginUserId, loginPassword);

		session.put("loginUser", loginDTO);

		/* 入力値からユーザー情報の検索を行います。
		ログイン認証が成功した場合、次の画面で
		「商品情報」が必要なため商品情報を取得します。*/

		// ログイン情報を比較
		if (((LoginDTO) session.get("loginUser")).getLoginFlg()) {
			result = SUCCESS;

			// アイテム情報を取得
			BuyItemDTO buyItemDTO = buyItemDAO.getBuyItemInfo();

			session.put("login_user_id", loginDTO.getLoginId());

			session.put("id", buyItemDTO.getId());
			session.put("buyItem_name", buyItemDTO.getItemName());
			session.put("buyItem_price", buyItemDTO.getItemPrice());

			return result;
		}
		return result;
	}
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}