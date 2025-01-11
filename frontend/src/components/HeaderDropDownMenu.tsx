import { DownOutlined } from "@ant-design/icons";
import type { MenuProps } from "antd";
import { Dropdown, Space } from "antd";
import { Link } from "react-router";

const items: MenuProps["items"] = [
  {
    key: "1",
    label: <Link to="/">Oglasi</Link>,
  },
  {
    key: "2",
    label: <Link to="/tema">Teme</Link>,
  },
];

const HeaderDropdownMenu = () => (
  <Dropdown menu={{ items }}>
    <a
      onClick={(e) => e.preventDefault()}
      style={{ color: "white", fontWeight: "bold" }}
    >
      <Space>
        Pregled kategorija
        <DownOutlined />
      </Space>
    </a>
  </Dropdown>
);

export default HeaderDropdownMenu;
