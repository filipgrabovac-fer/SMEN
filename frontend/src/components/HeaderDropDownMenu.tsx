import { DownOutlined } from "@ant-design/icons";
import type { MenuProps } from "antd";
import { Dropdown, Space } from "antd";
import { Link } from "react-router";

const items: MenuProps["items"] = [
  {
    key: "1",
    label: (
      <a
        target="_blank"
        rel="noopener noreferrer"
        href="https://www.antgroup.com"
      >
        1st menu item
      </a>
    ),
  },
  {
    key: "3",
    label: <Link to="">Kat 1</Link>,
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
