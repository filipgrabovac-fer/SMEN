import { useState } from "react";
import { Flex } from "antd";
import { Tema } from "../typings/tema";
import TemaHeader from "../components/TemaHeader";
import TemaTable from "../components/TemaTable";

const LayoutPregledTema = () => {
  const [data, setData] = useState<Tema[]>([
    {
      key: "1",
      title: "AI",
      description: "Introduction to AI",
      tags: ["futuristic", "sharp"],
    },
  ]);

  //   const addOglas = (newOglas: Oglas) => {
  //     setData((prevData) => [...prevData, newOglas]);
  //   };

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
      <TemaTable data={data} />
    </Flex>
  );
};

export default LayoutPregledTema;
