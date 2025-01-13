import { Flex } from "antd";
import HeaderDropdownMenu from "./GlobalHeaderDropDownMenu";
import { UserOutlined } from "@ant-design/icons";

const CustomHeader = () => {
  return (
    <Flex
      gap="middle"
      justify="flex-end"
      style={{ height: "100%", position: "sticky" }}
      align="center"
    >
      <HeaderDropdownMenu />
      <UserOutlined />
    </Flex>
  );
};

export default CustomHeader;
