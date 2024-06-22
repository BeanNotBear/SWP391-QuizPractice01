package model;

import java.util.Date;
import model.User;

/**
 * Lớp này đại diện cho một danh mục trong hệ thống.
 * Bao gồm các thuộc tính như ID, tên danh mục, ngày tạo, ngày cập nhật, người
 * tạo và người cập nhật.
 *
 * @author DELL
 */
public class Category {

    /**
     * ID của danh mục.
     */
    private int id;

    /**
     * Tên của danh mục.
     */
    private String Category_Name;

    /**
     * Ngày tạo danh mục.
     */
    private Date created_at;

    /**
     * Ngày cập nhật danh mục.
     */
    private Date upDate_at;

    /**
     * ID của người tạo.
     */
    private int created_by;

    /**
     * ID của người cập nhật.
     */
    private int update_by;

    /**
     * Người dùng liên quan.
     */
    private User user_id;

    /**
     * Constructor mặc định.
     */
    public Category() {
    }

    /**
     * Constructor có tham số để khởi tạo đối tượng Category với các giá trị cụ
     * thể.
     *
     * @param id ID của danh mục.
     * @param category_name Tên của danh mục.
     * @param created_at Ngày tạo danh mục.
     * @param upDate_at Ngày cập nhật danh mục.
     * @param created_by ID của người tạo.
     * @param update_by ID của người cập nhật.
     */
    public Category(int id, String category_name, Date created_at, Date upDate_at, int created_by, int update_by) {
        this.id = id;
        this.Category_Name = category_name;
        this.created_at = created_at;
        this.upDate_at = upDate_at;
        this.created_by = created_by;
        this.update_by = update_by;
    }

    /**
     * Constructor để khởi tạo đối tượng Category chỉ với tên danh mục.
     *
     * @param categoryName Tên của danh mục.
     */
    public Category(String categoryName) {
        this.Category_Name = categoryName;
    }

    /**
     * Trả về ID của danh mục.
     *
     * @return ID của danh mục.
     */
    public int getId() {
        return id;
    }

    /**
     * Thiết lập ID của danh mục.
     *
     * @param id ID của danh mục.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Trả về tên của danh mục.
     *
     * @return Tên của danh mục.
     */
    public String getCategory_Name() {
        return Category_Name;
    }

    /**
     * Thiết lập tên của danh mục.
     *
     * @param Category_Name Tên của danh mục.
     */
    public void setCategory_Name(String Category_Name) {
        this.Category_Name = Category_Name;
    }

    /**
     * Trả về ngày tạo danh mục.
     *
     * @return Ngày tạo danh mục.
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * Thiết lập ngày tạo danh mục.
     *
     * @param created_at Ngày tạo danh mục.
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * Trả về ngày cập nhật danh mục.
     *
     * @return Ngày cập nhật danh mục.
     */
    public Date getUpDate_at() {
        return upDate_at;
    }

    /**
     * Thiết lập ngày cập nhật danh mục.
     *
     * @param upDate_at Ngày cập nhật danh mục.
     */
    public void setUpDate_at(Date upDate_at) {
        this.upDate_at = upDate_at;
    }

    /**
     * Trả về ID của người tạo.
     *
     * @return ID của người tạo.
     */
    public int getCreated_by() {
        return created_by;
    }

    /**
     * Thiết lập ID của người tạo.
     *
     * @param created_by ID của người tạo.
     */
    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    /**
     * Trả về ID của người cập nhật.
     *
     * @return ID của người cập nhật.
     */
    public int getUpdate_by() {
        return update_by;
    }

    /**
     * Thiết lập ID của người cập nhật.
     *
     * @param update_by ID của người cập nhật.
     */
    public void setUpdate_by(int update_by) {
        this.update_by = update_by;
    }

    /**
     * Trả về người dùng liên quan.
     *
     * @return Người dùng liên quan.
     */
    public User getUser_id() {
        return user_id;
    }

    /**
     * Thiết lập người dùng liên quan.
     *
     * @param user_id Người dùng liên quan.
     */
    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

   
}
