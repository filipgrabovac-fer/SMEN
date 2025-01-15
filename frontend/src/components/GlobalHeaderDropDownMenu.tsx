import { DownOutlined } from "@ant-design/icons";
import type { MenuProps } from "antd";
import { Dropdown, Space } from "antd";
import { useState } from "react";
import PrijavaZaMentorstvoLayout from "./prijavaMentorstvo/PrijavaZaMentorstvoLayout";
import { router } from "../routes/router";

const role = "User";

const items: MenuProps["items"] = [
  {
    key: "/themes",
    label: "Teme",
  },
  {
    key: "/posts",
    label: "Oglasi",
  },
  {
    key: "/mentors-overview",
    label: "Pregled prijava",
  },
  {
    key: "/workshop-user",
    label: "My workshops",
  },
];
if (role === "User") {
  items.push({
    key: "prijava",
    label: "Prijava za mentorstvo",
  });
}

const HeaderDropdownMenu = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleMenuClick = (key: string) => {
    if (key === "prijava") {
      setIsModalOpen(true);
    } else {
      router.navigate({ to: key });
    }
  };

  return (
    <div>
      <Dropdown
        menu={{
          items,
          onClick: (e) => handleMenuClick(e.key),
        }}
      >
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

      {isModalOpen && (
        <PrijavaZaMentorstvoLayout
          open={isModalOpen}
          onClose={() => setIsModalOpen(false)}
        />
      )}
    </div>
  );
};

export default HeaderDropdownMenu;
