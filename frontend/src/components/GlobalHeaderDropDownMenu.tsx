import { DownOutlined } from "@ant-design/icons";
import type { MenuProps } from "antd";
import { Dropdown, Space } from "antd";
import { useState } from "react";
import PrijavaZaMentorstvoLayout from "./prijavaMentorstvo/PrijavaZaMentorstvoLayout";
import { router } from "../routes/router";
import { useGetUserApplication } from "./prijavaMentorstvo/hooks/useGetUserApplication.hook";

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
    label: "Moje radionice",
  },
];
if (role === "User") {
  items.push({
    key: "/prijava",
    label: "Prijava za mentorstvo",
  });
}
if (role === "User") {
  //promijenit u dobar role
  items.push({
    key: "/activity",
    label: "Activity log",
  });
}
items.push({
  key: "/login",
  label: (
    <p className="text-red-600" onClick={() => handleLogout()}>
      Odjava
    </p>
  ),
});

const handleLogout = () => {
  localStorage.clear();
};

const HeaderDropdownMenu = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const isAdmin = localStorage.getItem("userRole") === "ADMIN";
  const isUser = localStorage.getItem("userRole") === "USER";

  const { data: userAppliedForMentorship } = useGetUserApplication();

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
      key: "/workshop-user",
      label: "Moje radionice",
    },
  ];

  if (isAdmin)
    items.push({
      key: "/mentors-overview",
      label: "Pregled prijava",
    });

  if (isUser && !userAppliedForMentorship) {
    items.push({
      key: "prijava",
      label: "Prijava za mentorstvo",
    });
  }
  items.push({
    key: "/login",
    label: (
      <p className="text-red-600" onClick={() => handleLogout()}>
        Odjava
      </p>
    ),
  });
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
