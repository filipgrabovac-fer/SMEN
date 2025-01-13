import { Flex } from "antd";
import HeaderDropdownMenu from "./GlobalHeaderDropDownMenu";
import { UserOutlined } from "@ant-design/icons";

const GlobalHeader = () => {
  return (
    <Flex
      gap="middle"
      justify="flex-end"
      style={{
        height: "64px",
        position: "sticky",
        textAlign: "right",
        color: "#fff",
        paddingInline: "48px",
        lineHeight: "64px",
        backgroundColor: "#2c46b7",
      }}
      align="center"
    >
      <HeaderDropdownMenu />
      <UserOutlined />
    </Flex>
  );
};

export default GlobalHeader;
