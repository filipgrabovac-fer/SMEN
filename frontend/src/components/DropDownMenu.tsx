import React from "react";
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

const HeaderDropdownMenu: React.FC = () => (
  <Dropdown menu={{ items }}>
    <a onClick={(e) => e.preventDefault()}>
      <Space>
        Pregled kategorija
        <DownOutlined />
      </Space>
    </a>
  </Dropdown>
);

export default HeaderDropdownMenu;
