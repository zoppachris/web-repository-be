package id.wg.webrepo.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> {
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private String status;
	private T data;	
	private String message;

	public Response(T object)
	{
		this.status = Response.SUCCESS;
		this.data = object;
	}

	public void setError(String message) {
		this.status = Response.ERROR;
		this.message = message;
	}
	
	public void setSuccess(T object){
		this.status = Response.SUCCESS;
		this.data = object;
	}

	public static <T> Response<T> toSuccess(T data) {
		return Response.<T>builder()
				.data(data)
				.status(Response.SUCCESS)
				.build();
	}

	public static <T> Response<T> toError(T data, String message) {
		return Response.<T>builder()
				.data(data)
				.status(Response.ERROR)
				.message(message)
				.build();
	}
}
