export interface Oglas {
  key: string;
  autor: string;
  opis: string;
  datum: Date;
  naslovOglasa: string;
  details: string;
}
export interface OglasDetailsProps {
  title: string;
  content: string[];
  author: string;
  date: Date;
}
