import TemaHeader from "../components/OglasiHeader";
import { Flex } from "antd";
import TemaTable from "../components/OglasiTable";

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
      <TemaHeader />
      <TemaTable />
    </Flex>
  );
};

export default LayoutPregledTema;
