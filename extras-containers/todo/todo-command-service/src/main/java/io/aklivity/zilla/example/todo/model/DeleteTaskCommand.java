package io.aklivity.zilla.example.todo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Getter
public class DeleteTaskCommand implements Command
{
}
