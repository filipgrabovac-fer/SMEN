import { useState } from "react";
import { Flex } from "antd";
import OglasiHeader from "../components/OglasiHeader";
import OglasiTable from "../components/OglasiTable";
import { Oglas } from "../typings/oglas";

const LayoutPregledOglasa = () => {
  const [data, setData] = useState<Oglas[]>([
    {
      key: "1",
      name: "John Brown",
      opis: "Introduction to programming",
      datum: new Date("2023-12-01"),
      naslovOglasa: "Beginner Workshop",
      details: "https://example.com/john-workshop",
    },
    {
      key: "2",
      name: "Jane Doe",
      opis: "Advanced design techniques",
      datum: new Date("2023-11-15"),
      naslovOglasa: "Advanced Design Workshop",
      details: "https://example.com/jane-workshop",
    },
    {
      key: "3",
      name: "Alice Smith",
      opis: "Team collaboration strategies",
      datum: new Date("2023-10-20"),
      naslovOglasa: "Collaboration Workshop",
      details: "https://example.com/alice-workshop",
    },
  ]);

  const addOglas = (newOglas: Oglas) => {
    setData((prevData) => [...prevData, newOglas]);
  };

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
      <OglasiHeader onAddOglas={addOglas} />
      <OglasiTable data={data} />
    </Flex>
  );
};

export default LayoutPregledOglasa;
