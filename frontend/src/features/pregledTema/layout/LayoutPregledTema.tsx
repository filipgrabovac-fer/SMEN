import OglasiHeader from "../components/OglasiHeader";
import { Flex } from "antd";
import OglasiTable from "../components/OglasiTable";

const LayoutPregledTema = () => {
  return (
    <Flex
      className="w-full"
      justify="center"
      style={{
        width: "80%",
        height: "80%",
        margin: "0 auto",
      }}
      vertical
    >
      <OglasiHeader />
      <OglasiTable />
    </Flex>
  );
};

export default LayoutPregledTema;
