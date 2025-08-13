type ResponseStatusT<T> = {
  message: string;
  status: string;
  data: T;
};

export namespace CommonType {
  export type ResponseStatus<T> = ResponseStatusT<T>;
}
